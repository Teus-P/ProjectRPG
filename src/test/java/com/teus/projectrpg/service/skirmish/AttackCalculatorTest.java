package com.teus.projectrpg.service.skirmish;

import com.teus.projectrpg.armor.entity.ArmorEntity;
import com.teus.projectrpg.armor.entity.ArmorTypeEntity;
import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.type.ArmorType;
import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.character.entity.*;
import com.teus.projectrpg.characteristic.entity.CharacteristicEntity;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skirmish.dto.AttackQualityDto;
import com.teus.projectrpg.skirmish.dto.AttackResultDto;
import com.teus.projectrpg.skirmish.dto.BreakdownEntryDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.service.AttackCalculator;
import com.teus.projectrpg.skirmish.type.QualityRelevance;
import com.teus.projectrpg.talent.entity.TalentEntity;
import com.teus.projectrpg.talent.type.TalentType;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.entity.WeaponQualityEntity;
import com.teus.projectrpg.weapon.entity.WeaponQualityValueEntity;
import com.teus.projectrpg.weapon.entity.WeaponTypeEntity;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import com.teus.projectrpg.weapon.type.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttackCalculatorTest {

    private final AttackCalculator calculator = new AttackCalculator();
    private SkirmishCharacterEntity attacker;
    private SkirmishCharacterEntity target;
    private ReceivedDamageDto request;

    @BeforeEach
    void setUp() {
        attacker = createCharacter(40);
        target = createCharacter(30);
        request = new ReceivedDamageDto();
        request.setBodyLocalization(BodyLocalizationType.HEAD);
    }

    @Test
    void unarmedAttack_usesOnlyStrengthBonusAndSuccessPoints() {
        request.setSuccessPoints(2);

        AttackResultDto result = calculator.calculate(attacker, weapon(WeaponType.MELEE, 0), target, request);

        assertEquals(6, result.getDamage());
        assertEquals(0, result.getDestroyArmorValue());
        assertFalse(result.getIsWeaponUndamaging());
    }

    @Test
    void hackQuality_destroysOneArmorPoint() {
        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.HACK), target, request);

        assertEquals(9, result.getDamage());
        assertEquals(1, result.getDestroyArmorValue());
    }

    @Test
    void meleeAttack_addsStrikeMightyBlowTalent() {
        addTalent(attacker, TalentType.STRIKE_MIGHTY_BLOW, 2);

        AttackResultDto result = calculator.calculate(attacker, weapon(WeaponType.MELEE, 3), target, request);

        assertEquals(9, result.getDamage());
    }

    @Test
    void rangedAttack_ignoresStrengthWhenWeaponDoesNotUseIt() {
        WeaponEntity bow = weapon(WeaponType.RANGED, 4);
        bow.setIsUsingStrength(false);
        addTalent(attacker, TalentType.ACCURATE_SHOT, 1);

        AttackResultDto result = calculator.calculate(attacker, bow, target, request);

        assertEquals(5, result.getDamage());
    }

    @Test
    void impactQuality_addsUnitsDigitOfRoll() {
        request.setAttackRoll(34);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.IMPACT), target, request);

        assertEquals(13, result.getDamage());
        assertFalse(result.isMissingRoll());
    }

    @Test
    void impactQuality_withoutRoll_reportsMissingRoll() {
        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.IMPACT), target, request);

        assertEquals(9, result.getDamage());
        assertTrue(result.isMissingRoll());
    }

    @Test
    void damagingQuality_usesUnitsWhenHigherThanSuccessPoints() {
        request.setAttackRoll(34);
        request.setSuccessPoints(2);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.DAMAGING), target, request);

        assertEquals(11, result.getDamage());
    }

    @Test
    void damagingQuality_keepsSuccessPointsWhenHigherThanUnits() {
        request.setAttackRoll(31);
        request.setSuccessPoints(3);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.DAMAGING), target, request);

        assertEquals(10, result.getDamage());
    }

    @Test
    void undamagingQuality_overridesImpactAndSetsFlag() {
        request.setAttackRoll(39);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 2, WeaponQualityType.UNDAMAGING, WeaponQualityType.IMPACT), target, request);

        assertEquals(6, result.getDamage());
        assertTrue(result.getIsWeaponUndamaging());
        assertFalse(result.isMissingRoll());
    }

    @Test
    void tiringQuality_disablesImpactWithoutCharge() {
        request.setAttackRoll(36);
        WeaponEntity weapon = weapon(WeaponType.MELEE, 5, WeaponQualityType.IMPACT, WeaponQualityType.TIRING);

        assertEquals(9, calculator.calculate(attacker, weapon, target, request).getDamage());

        request.setIsCharging(true);
        assertEquals(15, calculator.calculate(attacker, weapon, target, request).getDamage());
    }

    @Test
    void preciseAndImpreciseQualities_changeDamageByOne() {
        assertEquals(10, calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.PRECISE), target, request).getDamage());
        assertEquals(8, calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.IMPRECISE), target, request).getDamage());
        assertEquals(8, calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.IMPRECISE, WeaponQualityType.PRECISE), target, request).getDamage());
    }

    @Test
    void penetratingQuality_ignoresNonMetalLayersAndFirstMetalPoint() {
        addArmor(target, ArmorType.NON_METAL, BodyLocalizationType.HEAD, 2);
        addArmor(target, ArmorType.METAL, BodyLocalizationType.HEAD, 2);
        setBodyArmor(target, BodyLocalizationType.HEAD, 4);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.PENETRATING), target, request);

        assertEquals(3, result.getIgnoredArmorPoints());
    }

    @Test
    void penetratingQuality_withoutMetalIgnoresAllArmor() {
        addArmor(target, ArmorType.NON_METAL, BodyLocalizationType.HEAD, 2);
        setBodyArmor(target, BodyLocalizationType.HEAD, 2);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.PENETRATING), target, request);

        assertEquals(2, result.getIgnoredArmorPoints());
    }

    @Test
    void damageOverride_replacesCalculatedDamageButKeepsArmorEffects() {
        request.setDamage(1);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.HACK), target, request);

        assertEquals(1, result.getDamage());
        assertTrue(result.isDamageOverridden());
        assertEquals(1, result.getDestroyArmorValue());
    }

    @Test
    void mitigation_subtractsToughnessBonusAndArmor() {
        setBodyArmor(target, BodyLocalizationType.HEAD, 2);

        AttackResultDto result = calculator.calculate(attacker, weapon(WeaponType.MELEE, 4), target, request);

        assertEquals(8, result.getDamage());
        assertEquals(3, result.getToughnessBonus());
        assertEquals(2, result.getArmorReduction());
        assertEquals(3, result.getWoundsLost());
    }

    @Test
    void mitigation_includesShieldAndAlwaysLeavesOneWound() {
        setBodyArmor(target, BodyLocalizationType.HEAD, 3);
        request.setShield(2);

        AttackResultDto result = calculator.calculate(attacker, weapon(WeaponType.MELEE, 0), target, request);

        assertEquals(5, result.getArmorReduction());
        assertEquals(1, result.getWoundsLost());
    }

    @Test
    void mitigation_undamagingDoublesArmorAndCanReduceWoundsToZero() {
        setBodyArmor(target, BodyLocalizationType.HEAD, 2);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 0, WeaponQualityType.UNDAMAGING), target, request);

        assertEquals(4, result.getArmorReduction());
        assertEquals(0, result.getWoundsLost());
    }

    @Test
    void mitigation_penetratingSkipsIgnoredArmorPoints() {
        addArmor(target, ArmorType.NON_METAL, BodyLocalizationType.HEAD, 2);
        setBodyArmor(target, BodyLocalizationType.HEAD, 2);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 4, WeaponQualityType.PENETRATING), target, request);

        assertEquals(0, result.getArmorReduction());
        assertEquals(5, result.getWoundsLost());
    }

    @Test
    void hackQuality_damagesTheShieldInsteadOfTheArmorWhenTheTargetParriedWithOne() {
        request.setShieldWeaponId(5L);

        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.HACK), target, request);

        assertTrue(result.isDamagesShield());
        assertEquals(1, result.getShieldPoints());
        assertEquals(0, result.getDestroyArmorValue());
        assertTrue(result.getBreakdown().contains(new BreakdownEntryDto(AttackCalculator.HACK_SHIELD, 1)));
        assertEquals(QualityRelevance.APPLIED, result.getQualities().getFirst().relevance());
    }

    @Test
    void hackQuality_withoutAShieldStillDestroysArmor() {
        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 5, WeaponQualityType.HACK), target, request);

        assertFalse(result.isDamagesShield());
        assertEquals(1, result.getDestroyArmorValue());
    }

    @Test
    void ignoringArmor_skipsArmorAndShieldButKeepsToughnessBonus() {
        setBodyArmor(target, BodyLocalizationType.HEAD, 3);
        request.setShield(2);
        request.setIsIgnoringArmor(true);

        AttackResultDto result = calculator.calculate(attacker, weapon(WeaponType.MELEE, 5), target, request);

        assertTrue(result.getIsIgnoringArmor());
        assertEquals(0, result.getArmorReduction());
        assertEquals(3, result.getToughnessBonus());
        assertEquals(result.getDamage() - 3, result.getWoundsLost());
    }

    @Test
    void ignoringArmor_isOffByDefault() {
        setBodyArmor(target, BodyLocalizationType.HEAD, 3);

        AttackResultDto result = calculator.calculate(attacker, weapon(WeaponType.MELEE, 5), target, request);

        assertFalse(result.getIsIgnoringArmor());
        assertEquals(3, result.getArmorReduction());
    }

    @Test
    void breakdown_listsStrengthAndWeaponDamage() {
        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.HACK), target, request);

        assertTrue(result.getBreakdown().contains(new BreakdownEntryDto(AttackCalculator.BASE_STRENGTH, 4)));
        assertTrue(result.getBreakdown().contains(new BreakdownEntryDto(AttackCalculator.WEAPON_DAMAGE, 3)));
    }

    @Test
    void qualities_markAppliedRemindersAndIrrelevantOnes() {
        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.HACK, WeaponQualityType.TRIP,
                        WeaponQualityType.TWO_HANDED), target, request);

        assertEquals(List.of(
                new AttackQualityDto(WeaponQualityType.HACK, null, QualityRelevance.APPLIED),
                new AttackQualityDto(WeaponQualityType.TRIP, null, QualityRelevance.REMINDER),
                new AttackQualityDto(WeaponQualityType.TWO_HANDED, null, QualityRelevance.NONE)),
                result.getQualities());
    }

    @Test
    void qualities_pummelMattersOnlyForHeadHits() {
        WeaponEntity weapon = weapon(WeaponType.MELEE, 3, WeaponQualityType.PUMMEL);

        request.setBodyLocalization(BodyLocalizationType.HEAD);
        assertEquals(QualityRelevance.REMINDER,
                calculator.calculate(attacker, weapon, target, request).getQualities().getFirst().relevance());

        request.setBodyLocalization(BodyLocalizationType.BODY);
        assertEquals(QualityRelevance.NONE,
                calculator.calculate(attacker, weapon, target, request).getQualities().getFirst().relevance());
    }

    @Test
    void qualities_impactWithoutRollIsOnlyAReminder() {
        AttackResultDto result = calculator.calculate(attacker,
                weapon(WeaponType.MELEE, 3, WeaponQualityType.IMPACT), target, request);

        assertEquals(QualityRelevance.REMINDER, result.getQualities().getFirst().relevance());
    }

    private SkirmishCharacterEntity createCharacter(int strength) {
        SkirmishCharacterEntity skirmishCharacter = new SkirmishCharacterEntity();
        CharacterEntity character = new CharacterEntity();
        skirmishCharacter.setCharacter(character);

        character.setCharacteristics(new ArrayList<>(List.of(
                characteristic(CharacteristicType.STRENGTH, strength),
                characteristic(CharacteristicType.TOUGHNESS, 30))));

        List<CharacterBodyLocalizationEntity> localizations = new ArrayList<>();
        for (BodyLocalizationType type : BodyLocalizationType.values()) {
            CharacterBodyLocalizationEntity localization = new CharacterBodyLocalizationEntity();
            BodyLocalizationEntity model = new BodyLocalizationEntity();
            model.setName(type);
            localization.setBodyLocalization(model);
            localizations.add(localization);
        }
        character.setBodyLocalizations(localizations);
        character.setTalents(new ArrayList<>());
        character.setArmors(new ArrayList<>());
        return skirmishCharacter;
    }

    private CharacterCharacteristicEntity characteristic(CharacteristicType type, int value) {
        CharacterCharacteristicEntity characteristic = new CharacterCharacteristicEntity();
        CharacteristicEntity model = new CharacteristicEntity();
        model.setName(type);
        characteristic.setCharacteristic(model);
        characteristic.setValue(value);
        return characteristic;
    }

    private WeaponEntity weapon(WeaponType type, int damage, WeaponQualityType... qualities) {
        WeaponEntity weapon = new WeaponEntity();
        weapon.setId(1L);
        WeaponTypeEntity weaponType = new WeaponTypeEntity();
        weaponType.setName(type);
        weapon.setWeaponType(weaponType);
        weapon.setDamage(damage);
        weapon.setIsUsingStrength(true);
        List<WeaponQualityValueEntity> values = new ArrayList<>();
        for (WeaponQualityType quality : qualities) {
            WeaponQualityEntity qualityEntity = new WeaponQualityEntity();
            qualityEntity.setName(quality);
            WeaponQualityValueEntity value = new WeaponQualityValueEntity();
            value.setWeaponQuality(qualityEntity);
            values.add(value);
        }
        weapon.setWeaponQualities(values);
        return weapon;
    }

    private void addTalent(SkirmishCharacterEntity skirmishCharacter, TalentType type, int value) {
        TalentEntity talent = new TalentEntity();
        talent.setName(type);
        CharacterTalentEntity characterTalent = new CharacterTalentEntity();
        characterTalent.setModel(talent);
        characterTalent.setValue(value);
        skirmishCharacter.getCharacter().getTalents().add(characterTalent);
    }

    private void addArmor(SkirmishCharacterEntity skirmishCharacter, ArmorType armorType,
                          BodyLocalizationType localization, int points) {
        ArmorEntity armor = new ArmorEntity();
        ArmorTypeEntity typeEntity = new ArmorTypeEntity();
        typeEntity.setName(armorType);
        armor.setArmorType(typeEntity);
        CharacterArmorEntity characterArmor = new CharacterArmorEntity();
        characterArmor.setArmor(armor);
        CharacterArmorBodyLocalizationEntity armorLocalization = new CharacterArmorBodyLocalizationEntity();
        BodyLocalizationEntity model = new BodyLocalizationEntity();
        model.setName(localization);
        armorLocalization.setBodyLocalization(model);
        armorLocalization.setArmorPoints(points);
        characterArmor.setArmorBodyLocalizations(new ArrayList<>(List.of(armorLocalization)));
        skirmishCharacter.getCharacter().getArmors().add(characterArmor);
    }

    private void setBodyArmor(SkirmishCharacterEntity skirmishCharacter, BodyLocalizationType type, int points) {
        skirmishCharacter.getCharacter().getBodyLocalizations().stream()
                .filter(localization -> localization.getBodyLocalization().getName() == type)
                .forEach(localization -> localization.setArmorPoints(points));
    }
}
