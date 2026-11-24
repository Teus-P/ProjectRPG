package com.teus.projectrpg.service.skirmish;

import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skill.type.SkillType;
import com.teus.projectrpg.skirmish.dto.AttackTestResultDto;
import com.teus.projectrpg.skirmish.dto.DefenderOptionDto;
import com.teus.projectrpg.skirmish.dto.TestSideDto;
import com.teus.projectrpg.skirmish.service.AttackTestResolver;
import com.teus.projectrpg.skirmish.service.AttackTestResolver.Participant;
import com.teus.projectrpg.skirmish.service.DiceRoller;
import com.teus.projectrpg.skirmish.type.AttackTestOutcome;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.entity.WeaponGroupEntity;
import com.teus.projectrpg.weapon.entity.WeaponTypeEntity;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import com.teus.projectrpg.weapon.type.WeaponType;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttackTestResolverTest {

    private int dice;
    private final Deque<Integer> queuedDice = new ArrayDeque<>();
    private final DiceRoller diceRoller = new DiceRoller() {
        @Override
        public int rollD100() {
            return queuedDice.isEmpty() ? dice : queuedDice.poll();
        }
    };
    private final AttackTestResolver resolver = new AttackTestResolver(diceRoller);

    private Participant side(int skillValue, Integer roll) {
        return new Participant(SkillType.MELEE_BASIC, null, skillValue, 0, 0, roll);
    }

    private AttackTestResultDto melee(Participant attacker, Participant defender) {
        return resolver.resolveMelee(attacker, defender, List.of());
    }

    @Test
    void test_successLevelIsTheDifferenceOfTens() {
        AttackTestResultDto success = resolver.resolveRanged(side(45, 23));
        AttackTestResultDto failure = resolver.resolveRanged(side(45, 67));

        assertTrue(success.getAttacker().success());
        assertEquals(2, success.getAttacker().successLevel());
        assertFalse(failure.getAttacker().success());
        assertEquals(-2, failure.getAttacker().successLevel());
    }

    @Test
    void test_rollOfFiveOrLessAlwaysSucceeds() {
        TestSideDto test = resolver.resolveRanged(side(0, 5)).getAttacker();

        assertTrue(test.success());
        assertEquals(0, test.successLevel());
    }

    @Test
    void test_rollOf96OrMoreAlwaysFailsAndNeverGivesPositiveSuccessLevels() {
        TestSideDto test = resolver.resolveRanged(side(120, 96)).getAttacker();

        assertFalse(test.success());
        assertEquals(0, test.successLevel());
    }

    @Test
    void test_rollOf100IsReadAsTheHighestRoll() {
        TestSideDto test = resolver.resolveRanged(side(80, 100)).getAttacker();

        assertFalse(test.success());
        assertEquals(-2, test.successLevel());
    }

    @Test
    void test_advantageAddsTenPerPointAndModifierIsAdded() {
        Participant participant = new Participant(SkillType.MELEE_BASIC, null, 40, 2, -5, 50);

        TestSideDto test = resolver.resolveRanged(participant).getAttacker();

        assertEquals(20, test.advantageBonus());
        assertEquals(55, test.target());
        assertEquals(0, test.successLevel());
    }

    @Test
    void test_targetNeverDropsBelowZero() {
        Participant participant = new Participant(SkillType.MELEE_BASIC, null, 10, 0, -40, 50);

        assertEquals(0, resolver.resolveRanged(participant).getAttacker().target());
    }

    @Test
    void test_withoutAnEnteredRollTheApplicationRolls() {
        dice = 17;

        TestSideDto rolled = resolver.resolveRanged(side(40, null)).getAttacker();
        TestSideDto entered = resolver.resolveRanged(side(40, 33)).getAttacker();

        assertEquals(17, rolled.roll());
        assertTrue(rolled.rolledAutomatically());
        assertEquals(33, entered.roll());
        assertFalse(entered.rolledAutomatically());
    }

    @Test
    void test_rollOutsideOneToHundredIsRejected() {
        assertThrows(ResponseStatusException.class, () -> resolver.resolveRanged(side(40, 0)));
        assertThrows(ResponseStatusException.class, () -> resolver.resolveRanged(side(40, 101)));
    }

    @Test
    void ranged_successLevelsGoToTheDamageAndAMissGivesNone() {
        AttackTestResultDto hit = resolver.resolveRanged(side(60, 20));
        AttackTestResultDto miss = resolver.resolveRanged(side(30, 80));

        assertFalse(hit.isOpposed());
        assertNull(hit.getDefender());
        assertEquals(AttackTestOutcome.HIT, hit.getOutcome());
        assertEquals(4, hit.getDamageSuccessLevel());
        assertEquals(AttackTestOutcome.ATTACKER_FAILED, miss.getOutcome());
        assertEquals(0, miss.getDamageSuccessLevel());
    }

    @Test
    void melee_attackerWithMoreSuccessLevelsHitsForTheDifference() {
        AttackTestResultDto result = melee(side(60, 20), side(50, 30));

        assertTrue(result.isOpposed());
        assertEquals(AttackTestOutcome.HIT, result.getOutcome());
        assertEquals(4 - 2, result.getDamageSuccessLevel());
    }

    @Test
    void melee_defenderWithMoreSuccessLevelsDefends() {
        AttackTestResultDto result = melee(side(60, 40), side(60, 20));

        assertEquals(AttackTestOutcome.DEFENDED, result.getOutcome());
        assertEquals(0, result.getDamageSuccessLevel());
    }

    @Test
    void melee_failedDefenceAddsItsFailureToTheDamage() {
        AttackTestResultDto result = melee(side(60, 40), side(40, 60));

        assertEquals(AttackTestOutcome.HIT, result.getOutcome());
        assertEquals(2 - (-2), result.getDamageSuccessLevel());
    }

    @Test
    void melee_attackerDoesNotHaveToSucceedToHit() {
        AttackTestResultDto result = melee(side(40, 50), side(30, 60));

        assertEquals(-1, result.getAttacker().successLevel());
        assertEquals(-3, result.getDefender().successLevel());
        assertEquals(AttackTestOutcome.HIT, result.getOutcome());
        assertEquals(2, result.getDamageSuccessLevel());
    }

    @Test
    void melee_attackerWithFewerSuccessLevelsIsDefendedEvenWhenBothFail() {
        AttackTestResultDto defenderSucceeded = melee(side(40, 50), side(60, 50));
        AttackTestResultDto bothFailed = melee(side(30, 60), side(40, 50));

        assertEquals(AttackTestOutcome.DEFENDED, defenderSucceeded.getOutcome());
        assertEquals(AttackTestOutcome.DEFENDED, bothFailed.getOutcome());
        assertEquals(0, bothFailed.getDamageSuccessLevel());
    }

    @Test
    void melee_equalSuccessLevelsGoToTheHigherSkill() {
        // the skill counts, not the target value with advantage or modifiers
        Participant advantagedAttacker = new Participant(SkillType.MELEE_BASIC, null, 40, 2, 0, 32);
        Participant advantagedDefender = new Participant(SkillType.MELEE_BASIC, null, 40, 2, 0, 32);

        AttackTestResultDto defenderHigherSkill = melee(advantagedAttacker, side(50, 22));
        AttackTestResultDto attackerHigherSkill = melee(side(50, 22), advantagedDefender);

        assertEquals(AttackTestOutcome.DEFENDED, defenderHigherSkill.getOutcome());
        assertEquals(AttackTestOutcome.HIT, attackerHigherSkill.getOutcome());
        assertEquals(0, attackerHigherSkill.getDamageSuccessLevel());
    }

    @Test
    void melee_tieWithEqualSkillsIsRolledAgainUntilSomebodyWins() {
        queuedDice.addAll(List.of(32, 32, 20, 50));

        AttackTestResultDto result = melee(side(60, null), side(60, null));

        assertEquals(1, result.getRepeatedTimes());
        assertEquals(20, result.getAttacker().roll());
        assertEquals(50, result.getDefender().roll());
        assertEquals(AttackTestOutcome.HIT, result.getOutcome());
        assertEquals(4 - 1, result.getDamageSuccessLevel());
    }

    @Test
    void melee_tieWithEqualSkillsIsNotRolledAgainWhenARollWasEnteredByHand() {
        AttackTestResultDto bothEntered = melee(side(60, 32), side(60, 35));
        queuedDice.addAll(List.of(33));
        AttackTestResultDto oneEntered = melee(side(60, 32), side(60, null));

        assertEquals(AttackTestOutcome.TIE, bothEntered.getOutcome());
        assertEquals(0, bothEntered.getRepeatedTimes());
        assertEquals(AttackTestOutcome.TIE, oneEntered.getOutcome());
        assertEquals(0, oneEntered.getRepeatedTimes());
        assertEquals(0, oneEntered.getDamageSuccessLevel());
    }

    @Test
    void melee_aTieThatNeverEndsStopsAfterTheLimit() {
        dice = 32;

        AttackTestResultDto result = melee(side(60, null), side(60, null));

        assertEquals(AttackTestResolver.MAX_REPEATS, result.getRepeatedTimes());
        assertEquals(AttackTestOutcome.TIE, result.getOutcome());
    }

    @Test
    void melee_keepsTheDefenderOptionsAndTheHitLocationFromTheAttackersRoll() {
        List<DefenderOptionDto> options = List.of(new DefenderOptionDto(SkillType.DODGE, CharacteristicType.AGILITY, 40));

        AttackTestResultDto result = resolver.resolveMelee(side(60, 34), side(40, 80), options);

        assertEquals(options, result.getDefenderOptions());
        assertEquals(BodyLocalizationType.RIGHT_ARM, result.getBodyLocalization());
    }

    @Test
    void hitLocation_readsTheRollWithItsDigitsReversed() {
        assertEquals(BodyLocalizationType.HEAD, AttackTestResolver.hitLocation(10));
        assertEquals(BodyLocalizationType.HEAD, AttackTestResolver.hitLocation(90));
        assertEquals(BodyLocalizationType.LEFT_ARM, AttackTestResolver.hitLocation(11));
        assertEquals(BodyLocalizationType.LEFT_ARM, AttackTestResolver.hitLocation(91));
        assertEquals(BodyLocalizationType.RIGHT_ARM, AttackTestResolver.hitLocation(34));
        assertEquals(BodyLocalizationType.RIGHT_ARM, AttackTestResolver.hitLocation(52));
        assertEquals(BodyLocalizationType.BODY, AttackTestResolver.hitLocation(5));
        assertEquals(BodyLocalizationType.BODY, AttackTestResolver.hitLocation(77));
        assertEquals(BodyLocalizationType.LEFT_LEG, AttackTestResolver.hitLocation(98));
        assertEquals(BodyLocalizationType.RIGHT_LEG, AttackTestResolver.hitLocation(100));
        assertEquals(BodyLocalizationType.RIGHT_LEG, AttackTestResolver.hitLocation(9));
    }

    @Test
    void skillFor_mapsTheWeaponGroupToTheSkill() {
        assertEquals(SkillType.MELEE_BASIC, AttackTestResolver.skillFor(weapon(WeaponType.MELEE, WeaponGroupType.BASIC)));
        assertEquals(SkillType.MELEE_BRAWLING, AttackTestResolver.skillFor(weapon(WeaponType.MELEE, WeaponGroupType.BRAWLING)));
        assertEquals(SkillType.RANGED_BOW, AttackTestResolver.skillFor(weapon(WeaponType.RANGED, WeaponGroupType.BOW)));
        assertEquals(SkillType.RANGED_THROWING, AttackTestResolver.skillFor(weapon(WeaponType.RANGED, WeaponGroupType.THROWING)));
        assertNull(AttackTestResolver.skillFor(weapon(WeaponType.MELEE, null)));
    }

    private WeaponEntity weapon(WeaponType type, WeaponGroupType group) {
        WeaponEntity weapon = new WeaponEntity();
        WeaponTypeEntity weaponType = new WeaponTypeEntity();
        weaponType.setName(type);
        weapon.setWeaponType(weaponType);
        if (group != null) {
            WeaponGroupEntity weaponGroup = new WeaponGroupEntity();
            weaponGroup.setName(group);
            weapon.setWeaponGroup(weaponGroup);
        }
        return weapon;
    }
}
