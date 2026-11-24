package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skill.type.SkillType;
import com.teus.projectrpg.skirmish.dto.AttackTestResultDto;
import com.teus.projectrpg.skirmish.dto.DefenderOptionDto;
import com.teus.projectrpg.skirmish.dto.TestSideDto;
import com.teus.projectrpg.skirmish.type.AttackTestOutcome;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import com.teus.projectrpg.weapon.type.WeaponType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * The rules of an attack test (WFRP 4e): d100 against a target value, Success Levels, the opposed test in melee and
 * the hit location. It only rolls dice (through {@link DiceRoller}); it knows nothing about characters or entities.
 */
@Component
@RequiredArgsConstructor
public class AttackTestResolver {

    private static final int ADVANTAGE_BONUS = 10;
    private static final int AUTOMATIC_SUCCESS_MAX = 5;
    private static final int AUTOMATIC_FAILURE_MIN = 96;
    /** A safety net: equal skills tie only now and then, so this is never reached with real dice. */
    public static final int MAX_REPEATS = 50;

    private final DiceRoller diceRoller;

    /**
     * What one side of the test is made of. {@code skillType} is empty when the characteristic stands in for a skill
     * the character does not have, {@code roll} is empty when the application should roll.
     */
    public record Participant(SkillType skillType, CharacteristicType characteristicType, int skillValue,
                              int advantage, int modifier, Integer roll) {
    }

    /** A ranged attack: one test against the attacker's skill, there is nothing to oppose. */
    public AttackTestResultDto resolveRanged(Participant attacker) {
        AttackTestResultDto result = new AttackTestResultDto();
        TestSideDto attackerTest = test(attacker);
        result.setAttacker(attackerTest);
        result.setBodyLocalization(hitLocation(attackerTest.roll()));
        if (attackerTest.success()) {
            result.setOutcome(AttackTestOutcome.HIT);
            result.setDamageSuccessLevel(attackerTest.successLevel());
        } else {
            result.setOutcome(AttackTestOutcome.ATTACKER_FAILED);
        }
        return result;
    }

    /**
     * Melee: both sides roll and the Success Levels are compared as they are, so the attacker does not have to succeed:
     * -1 against a defender's -3 is a hit for 2. When the Success Levels are equal, the higher skill wins. When the
     * skills are equal too, the test is rolled again until somebody wins. That is only possible when the application
     * rolled both sides; if a roll was entered by hand the result is a {@link AttackTestOutcome#TIE} to roll again.
     */
    public AttackTestResultDto resolveMelee(Participant attacker, Participant defender,
                                            List<DefenderOptionDto> defenderOptions) {
        AttackTestResultDto result = new AttackTestResultDto();
        result.setOpposed(true);
        result.setDefenderOptions(defenderOptions);
        boolean canRepeat = attacker.roll() == null && defender.roll() == null;

        TestSideDto attackerTest = test(attacker);
        TestSideDto defenderTest = test(defender);
        int repeats = 0;
        while (isTie(attackerTest, defenderTest) && canRepeat && repeats < MAX_REPEATS) {
            repeats++;
            attackerTest = test(attacker);
            defenderTest = test(defender);
        }
        result.setAttacker(attackerTest);
        result.setDefender(defenderTest);
        result.setRepeatedTimes(repeats);
        result.setBodyLocalization(hitLocation(attackerTest.roll()));

        if (isTie(attackerTest, defenderTest)) {
            result.setOutcome(AttackTestOutcome.TIE);
        } else if (attackerWins(attackerTest, defenderTest)) {
            result.setOutcome(AttackTestOutcome.HIT);
            result.setDamageSuccessLevel(Math.max(attackerTest.successLevel() - defenderTest.successLevel(), 0));
        } else {
            result.setOutcome(AttackTestOutcome.DEFENDED);
        }
        return result;
    }

    /** Equal Success Levels and equal skills (the skill itself, without advantage or modifiers). */
    private boolean isTie(TestSideDto attacker, TestSideDto defender) {
        return attacker.successLevel() == defender.successLevel() && attacker.skillValue() == defender.skillValue();
    }

    private boolean attackerWins(TestSideDto attacker, TestSideDto defender) {
        if (attacker.successLevel() != defender.successLevel()) {
            return attacker.successLevel() > defender.successLevel();
        }
        return attacker.skillValue() > defender.skillValue();
    }

    private TestSideDto test(Participant participant) {
        boolean rolledAutomatically = participant.roll() == null;
        int roll = rolledAutomatically ? diceRoller.rollD100() : validated(participant.roll());
        int advantageBonus = participant.advantage() * ADVANTAGE_BONUS;
        int target = Math.max(participant.skillValue() + advantageBonus + participant.modifier(), 0);

        boolean success = roll <= AUTOMATIC_SUCCESS_MAX || (roll < AUTOMATIC_FAILURE_MIN && roll <= target);
        int successLevel = target / 10 - roll / 10;
        // an automatic result never counts as the opposite one, it is at best +0 / -0
        if (success) {
            successLevel = Math.max(successLevel, 0);
        } else {
            successLevel = Math.min(successLevel, 0);
        }
        return new TestSideDto(participant.skillType(), participant.characteristicType(), participant.skillValue(),
                advantageBonus, participant.modifier(), target, roll, rolledAutomatically, successLevel, success);
    }

    private int validated(int roll) {
        if (roll < 1 || roll > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A d100 roll has to be between 1 and 100");
        }
        return roll;
    }

    /**
     * The attacker's roll with its digits reversed (a 100 is read as 00). 01-09 head, 10-24 left arm, 25-44 right arm,
     * 45-79 body, 80-89 left leg, 90-00 right leg.
     */
    public static BodyLocalizationType hitLocation(int roll) {
        int reversed = Integer.parseInt(new StringBuilder(String.format("%02d", roll % 100)).reverse().toString());
        if (reversed == 0) {
            reversed = 100;
        }
        if (reversed <= 9) {
            return BodyLocalizationType.HEAD;
        } else if (reversed <= 24) {
            return BodyLocalizationType.LEFT_ARM;
        } else if (reversed <= 44) {
            return BodyLocalizationType.RIGHT_ARM;
        } else if (reversed <= 79) {
            return BodyLocalizationType.BODY;
        } else if (reversed <= 89) {
            return BodyLocalizationType.LEFT_LEG;
        }
        return BodyLocalizationType.RIGHT_LEG;
    }

    /**
     * The skill a weapon is used with, by its group. Empty when the weapon has no group.
     */
    public static SkillType skillFor(WeaponEntity weapon) {
        if (weapon.getWeaponGroup() == null || weapon.getWeaponGroup().getName() == null) {
            return null;
        }
        boolean isMelee = weapon.getWeaponType() != null && weapon.getWeaponType().getName() == WeaponType.MELEE;
        WeaponGroupType group = weapon.getWeaponGroup().getName();
        return switch (group) {
            case BASIC -> isMelee ? SkillType.MELEE_BASIC : null;
            case CAVALRY -> SkillType.MELEE_CAVALRY;
            case FENCING -> SkillType.MELEE_FENCING;
            case BRAWLING -> SkillType.MELEE_BRAWLING;
            case FLAIL -> SkillType.MELEE_FLAIL;
            case PARRY -> SkillType.MELEE_PARRY;
            case POLEARM -> SkillType.MELEE_POLEARM;
            case TWO_HANDED -> SkillType.MELEE_TWO_HANDED;
            case BLACKPOWDER, BLACKPOWDER_AMMUNITION -> SkillType.RANGED_BLACKPOWDER;
            case BOW, BOW_AMMUNITION -> SkillType.RANGED_BOW;
            case CROSSBOW, CROSSBOW_AMMUNITION -> SkillType.RANGED_CROSSBOW;
            case ENGINEERING -> SkillType.RANGED_ENGINEERING;
            case ENTANGLING -> SkillType.RANGED_ENTANGLING;
            case EXPLOSIVES -> SkillType.RANGED_EXPLOSIVES;
            case SLING, SLING_AMMUNITION -> SkillType.RANGED_SLING;
            case THROWING -> SkillType.RANGED_THROWING;
        };
    }
}
