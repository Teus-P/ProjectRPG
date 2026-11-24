package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.armor.type.ArmorType;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.character.entity.CharacterArmorEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skirmish.dto.AttackQualityDto;
import com.teus.projectrpg.skirmish.dto.AttackResultDto;
import com.teus.projectrpg.skirmish.dto.BreakdownEntryDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.type.QualityRelevance;
import com.teus.projectrpg.talent.type.TalentType;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import com.teus.projectrpg.weapon.type.WeaponType;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Pure game rules for an attack: turns attacker, weapon, target and the roll into damage and armor effects.
 * It never modifies any entity, so it is safe to use for a preview.
 */
@Component
public class AttackCalculator {

    public static final String BASE_STRENGTH = "BASE_STRENGTH";
    public static final String WEAPON_DAMAGE = "WEAPON_DAMAGE";
    public static final String TALENT_BONUS = "TALENT_BONUS";
    public static final String SUCCESS_POINTS = "SUCCESS_POINTS";
    public static final String HACK_SHIELD = "HACK_SHIELD";

    /**
     * Qualities with an effect in a single attack that the application cannot resolve itself
     * (tests, choices, conditions). Passive ones, such as Two-handed or Defensive, are not listed here.
     */
    private static final Set<WeaponQualityType> ACTION_QUALITIES = EnumSet.of(
            WeaponQualityType.BLACKPOWDER, WeaponQualityType.BLAST, WeaponQualityType.SPREAD,
            WeaponQualityType.DISTRACT, WeaponQualityType.ENTANGLE, WeaponQualityType.IMPALE,
            WeaponQualityType.PUMMEL, WeaponQualityType.TRIP, WeaponQualityType.SLASH,
            WeaponQualityType.INFECTED, WeaponQualityType.INCENDIARY, WeaponQualityType.GRAPPLE,
            WeaponQualityType.DANGEROUS, WeaponQualityType.BALLOCK_KNIFE, WeaponQualityType.LANCE,
            WeaponQualityType.LARGE_BULLET_AND_POWDER, WeaponQualityType.TIRING,
            WeaponQualityType.IMPACT, WeaponQualityType.DAMAGING);

    /**
     * What the target takes off an attack: Toughness bonus plus armor (without the ignored points, doubled against
     * an undamaging weapon). Used both for the preview and for applying the damage, so they cannot differ.
     */
    public record Mitigation(int toughnessBonus, int armorReduction, int finalDamage, int woundsLost) {
    }

    public Mitigation mitigate(int damage, SkirmishCharacterEntity target, ReceivedDamageDto request,
                               int ignoredArmorPoints, boolean isIgnoringArmor, boolean isUndamaging) {
        int toughnessBonus = (target.getCharacteristicValueByType(CharacteristicType.TOUGHNESS) / 10) % 100;
        int armor = 0;
        if (!isIgnoringArmor) {
            armor = Math.max(target.getArmorForLocalization(request.getBodyLocalization()) - ignoredArmorPoints, 0)
                    + request.getShield();
        }
        if (isUndamaging) {
            armor *= 2;
        }
        int finalDamage = damage - toughnessBonus - armor;
        int woundsLost = isUndamaging ? Math.max(finalDamage, 0) : Math.max(finalDamage, 1);
        return new Mitigation(toughnessBonus, armor, finalDamage, woundsLost);
    }

    /**
     * The damage the weapon does before any roll: Strength bonus, weapon damage and the talent. It tells which of
     * the attacker's weapons is the strongest.
     */
    public int baseDamage(SkirmishCharacterEntity attacker, WeaponEntity weapon) {
        return baseStrengthDamage(attacker, weapon) + weapon.getDamage() + talentDamage(attacker, weapon);
    }

    public AttackResultDto calculate(SkirmishCharacterEntity attacker, WeaponEntity weapon,
                                     SkirmishCharacterEntity target, ReceivedDamageDto request) {
        AttackResultDto result = new AttackResultDto();
        Set<WeaponQualityType> qualities = qualitiesOf(weapon);
        boolean isUndamaging = qualities.contains(WeaponQualityType.UNDAMAGING);
        boolean canUseHeavyQualities = !qualities.contains(WeaponQualityType.TIRING)
                || Boolean.TRUE.equals(request.getIsCharging());
        Integer roll = request.getAttackRoll();
        Integer units = roll == null ? null : roll % 10;

        int damage = 0;
        damage += add(result, BASE_STRENGTH, baseStrengthDamage(attacker, weapon));
        damage += add(result, WEAPON_DAMAGE, weapon.getDamage());
        damage += add(result, TALENT_BONUS, talentDamage(attacker, weapon));

        int successPoints = request.getSuccessPoints();
        damage += add(result, SUCCESS_POINTS, successPoints);

        if (qualities.contains(WeaponQualityType.IMPRECISE)) {
            successPoints += add(result, WeaponQualityType.IMPRECISE.name(), -1);
            damage -= 1;
        } else if (qualities.contains(WeaponQualityType.PRECISE)) {
            successPoints += add(result, WeaponQualityType.PRECISE.name(), 1);
            damage += 1;
        }

        boolean needsRoll = false;
        if (!isUndamaging && canUseHeavyQualities) {
            if (qualities.contains(WeaponQualityType.DAMAGING)) {
                if (units == null) {
                    needsRoll = true;
                } else if (units > successPoints) {
                    damage += add(result, WeaponQualityType.DAMAGING.name(), units - successPoints);
                }
            }
            if (qualities.contains(WeaponQualityType.IMPACT)) {
                if (units == null) {
                    needsRoll = true;
                } else {
                    damage += add(result, WeaponQualityType.IMPACT.name(), units);
                }
            }
        }
        result.setMissingRoll(needsRoll);

        if (isUndamaging) {
            result.setIsWeaponUndamaging(true);
            add(result, WeaponQualityType.UNDAMAGING.name(), 0);
        }
        if (qualities.contains(WeaponQualityType.HACK)) {
            if (request.getShieldWeaponId() != null) {
                result.setDamagesShield(true);
                result.setShieldPoints(1);
                add(result, HACK_SHIELD, 1);
            } else {
                result.setDestroyArmorValue(1);
                add(result, WeaponQualityType.HACK.name(), 1);
            }
        }
        if (qualities.contains(WeaponQualityType.PENETRATING)) {
            int ignored = penetratedArmorPoints(target, request);
            result.setIgnoredArmorPoints(ignored);
            add(result, WeaponQualityType.PENETRATING.name(), ignored);
        }

        result.setQualities(describeQualities(weapon, result, request));

        boolean overridden = request.getDamage() != null;
        result.setDamageOverridden(overridden);
        result.setDamage(overridden ? request.getDamage() : damage);

        // not a weapon quality: chosen by the player, e.g. for a magical weapon that ignores armor
        boolean isIgnoringArmor = Boolean.TRUE.equals(request.getIsIgnoringArmor());
        result.setIsIgnoringArmor(isIgnoringArmor);

        Mitigation mitigation = mitigate(result.getDamage(), target, request, result.getIgnoredArmorPoints(),
                isIgnoringArmor, result.getIsWeaponUndamaging());
        result.setToughnessBonus(mitigation.toughnessBonus());
        result.setArmorReduction(mitigation.armorReduction());
        result.setWoundsLost(mitigation.woundsLost());
        return result;
    }

    private List<AttackQualityDto> describeQualities(WeaponEntity weapon, AttackResultDto result,
                                                     ReceivedDamageDto request) {
        Set<String> appliedKeys = result.getBreakdown().stream()
                .map(BreakdownEntryDto::key)
                .collect(Collectors.toSet());
        return weapon.getWeaponQualities().stream()
                .map(value -> {
                    WeaponQualityType quality = value.getWeaponQuality().getName();
                    QualityRelevance relevance = QualityRelevance.NONE;
                    boolean damagedShield = quality == WeaponQualityType.HACK && appliedKeys.contains(HACK_SHIELD);
                    if (appliedKeys.contains(quality.name()) || damagedShield) {
                        relevance = QualityRelevance.APPLIED;
                    } else if (ACTION_QUALITIES.contains(quality)
                            && (quality != WeaponQualityType.PUMMEL
                            || request.getBodyLocalization() == BodyLocalizationType.HEAD)) {
                        relevance = QualityRelevance.REMINDER;
                    }
                    return new AttackQualityDto(quality, value.getValue(), relevance);
                })
                .toList();
    }

    private int add(AttackResultDto result, String key, int value) {
        if (value != 0 || isQualityKey(key)) {
            result.getBreakdown().add(new BreakdownEntryDto(key, value));
        }
        return value;
    }

    private boolean isQualityKey(String key) {
        return EnumSet.allOf(WeaponQualityType.class).stream().anyMatch(quality -> quality.name().equals(key));
    }

    private Set<WeaponQualityType> qualitiesOf(WeaponEntity weapon) {
        Set<WeaponQualityType> qualities = EnumSet.noneOf(WeaponQualityType.class);
        weapon.getWeaponQualities().forEach(value -> qualities.add(value.getWeaponQuality().getName()));
        return qualities;
    }

    private int strengthBonus(SkirmishCharacterEntity attacker) {
        return attacker.getCharacteristicValueByType(CharacteristicType.STRENGTH) / 10;
    }

    private int baseStrengthDamage(SkirmishCharacterEntity attacker, WeaponEntity weapon) {
        return switch (weapon.getWeaponType().getName()) {
            case MELEE -> strengthBonus(attacker);
            case RANGED -> Boolean.TRUE.equals(weapon.getIsUsingStrength()) ? strengthBonus(attacker) : 0;
            case AMMUNITION -> 0;
        };
    }

    private int talentDamage(SkirmishCharacterEntity attacker, WeaponEntity weapon) {
        TalentType talent = switch (weapon.getWeaponType().getName()) {
            case MELEE -> TalentType.STRIKE_MIGHTY_BLOW;
            case RANGED -> TalentType.ACCURATE_SHOT;
            case AMMUNITION -> null;
        };
        if (talent == null) {
            return 0;
        }
        return attacker.getCharacter().getTalents().stream()
                .filter(characterTalent -> characterTalent.getModel().getName() == talent)
                .mapToInt(characterTalent -> characterTalent.getValue())
                .findFirst()
                .orElse(0);
    }

    /**
     * Penetrating: armor points of non-metal layers are ignored, and so is the first point of the rest.
     */
    private int penetratedArmorPoints(SkirmishCharacterEntity target, ReceivedDamageDto request) {
        if (request.getBodyLocalization() == null) {
            return 0;
        }
        int total = target.getArmorForLocalization(request.getBodyLocalization());
        int metal = 0;
        for (CharacterArmorEntity armor : target.getCharacter().getArmors()) {
            if (armor.getArmor().getArmorType().getName() != ArmorType.METAL) {
                continue;
            }
            metal += armor.getArmorBodyLocalizations().stream()
                    .filter(localization -> localization.getBodyLocalization().getName() == request.getBodyLocalization())
                    .mapToInt(localization -> localization.getArmorPoints())
                    .sum();
        }
        int remaining = Math.max(metal - 1, 0);
        return Math.max(total - remaining, 0);
    }
}
