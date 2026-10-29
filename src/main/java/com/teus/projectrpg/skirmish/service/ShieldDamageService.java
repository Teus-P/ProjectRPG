package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.entity.CharacterWeaponEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.skirmish.dto.ShieldChangeDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.entity.WeaponQualityValueEntity;
import com.teus.projectrpg.weapon.repository.WeaponRepository;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

/**
 * Damaging a shield lowers its Shield value (by one for a Hack hit). The shield is replaced in the equipment by a copy of the same
 * weapon with a lower value (created once and reused later), and it is removed when the value drops to 0.
 */
@Service
@RequiredArgsConstructor
public class ShieldDamageService {

    private static final String VARIANT_SUFFIX = "_SHIELD_";
    private static final String VARIANT_PATTERN = VARIANT_SUFFIX + "\\d+$";
    private static final long FIRST_CUSTOM_WEAPON_ID = 5000L;

    private final WeaponRepository weaponRepository;

    public int shieldValue(SkirmishCharacterEntity target, Long shieldWeaponId) {
        return valueOf(findEntry(target, shieldWeaponId).getWeapon());
    }

    /**
     * What would happen to the shield. Does not change anything.
     */
    public ShieldChangeDto describe(SkirmishCharacterEntity target, Long shieldWeaponId, int points) {
        WeaponEntity weapon = findEntry(target, shieldWeaponId).getWeapon();
        int before = valueOf(weapon);
        return new ShieldChangeDto(rootOf(weapon).getNameTranslation(), before, Math.max(before - points, 0));
    }

    public ShieldChangeDto apply(SkirmishCharacterEntity target, Long shieldWeaponId, int points) {
        CharacterEntity character = target.getCharacter();
        CharacterWeaponEntity entry = findEntry(target, shieldWeaponId);
        WeaponEntity weapon = entry.getWeapon();
        WeaponEntity root = rootOf(weapon);
        int before = valueOf(weapon);
        int after = Math.max(before - points, 0);

        if (entry.getValue() > 1) {
            entry.setValue(entry.getValue() - 1);
        } else {
            character.getWeapons().remove(entry);
        }

        if (after > 0) {
            WeaponEntity variant = findOrCreateVariant(root, after);
            CharacterWeaponEntity existing = character.getWeapons().stream()
                    .filter(owned -> owned.getWeapon().getId().equals(variant.getId()))
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                existing.setValue(existing.getValue() + 1);
            } else {
                CharacterWeaponEntity added = new CharacterWeaponEntity();
                added.setCharacter(character);
                added.setWeapon(variant);
                added.setValue(1);
                character.getWeapons().add(added);
            }
        }
        return new ShieldChangeDto(root.getNameTranslation(), before, after);
    }

    private CharacterWeaponEntity findEntry(SkirmishCharacterEntity target, Long shieldWeaponId) {
        CharacterWeaponEntity entry = target.getCharacter().getWeapons().stream()
                .filter(owned -> owned.getWeapon().getId().equals(shieldWeaponId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The target does not have weapon " + shieldWeaponId));
        valueOf(entry.getWeapon());
        return entry;
    }

    private int valueOf(WeaponEntity weapon) {
        WeaponQualityValueEntity shield = weapon.getWeaponQualities().stream()
                .filter(quality -> quality.getWeaponQuality().getName() == WeaponQualityType.SHIELD)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Weapon " + weapon.getId() + " is not a shield"));
        try {
            return Integer.parseInt(shield.getValue().trim());
        } catch (RuntimeException exception) {
            return 1;
        }
    }

    private WeaponEntity rootOf(WeaponEntity weapon) {
        if (!Boolean.TRUE.equals(weapon.getIsDamagedVariant())) {
            return weapon;
        }
        WeaponEntity root = weaponRepository.findWeaponEntityByName(weapon.getName().replaceFirst(VARIANT_PATTERN, ""));
        return root != null ? root : weapon;
    }

    /**
     * The first level of damage is "(Uszkodzona)", further ones are numbered, as translations must be unique.
     */
    private String variantTranslation(WeaponEntity root, int shieldValue) {
        int lostPoints = valueOf(root) - shieldValue;
        return root.getNameTranslation() + (lostPoints <= 1 ? " (Uszkodzona)" : " (Uszkodzona " + lostPoints + ")");
    }

    private WeaponEntity findOrCreateVariant(WeaponEntity root, int shieldValue) {
        String name = root.getName() + VARIANT_SUFFIX + shieldValue;
        String nameTranslation = variantTranslation(root, shieldValue);
        WeaponEntity existing = weaponRepository.findWeaponEntityByName(name);
        if (existing != null) {
            if (!nameTranslation.equals(existing.getNameTranslation())) {
                existing.setNameTranslation(nameTranslation);
                return weaponRepository.save(existing);
            }
            return existing;
        }

        WeaponEntity variant = new WeaponEntity();
        variant.setName(name);
        variant.setNameTranslation(nameTranslation);
        variant.setWeaponType(root.getWeaponType());
        variant.setWeaponGroup(root.getWeaponGroup());
        variant.setWeaponReach(root.getWeaponReach());
        variant.setWeaponRange(root.getWeaponRange());
        variant.setIsUsingStrength(root.getIsUsingStrength());
        variant.setIsUsingStrengthInRange(root.getIsUsingStrengthInRange());
        variant.setDamage(root.getDamage());
        variant.setPrice(root.getPrice());
        variant.setEncumbrance(root.getEncumbrance());
        variant.setAvailability(root.getAvailability());
        variant.setIsBaseWeapon(false);
        variant.setIsDamagedVariant(true);

        int maxId = weaponRepository.findMaxId();
        variant.setId(maxId < FIRST_CUSTOM_WEAPON_ID ? FIRST_CUSTOM_WEAPON_ID : maxId + 1L);

        variant.setWeaponQualities(new ArrayList<>());
        for (WeaponQualityValueEntity quality : root.getWeaponQualities()) {
            WeaponQualityValueEntity copy = new WeaponQualityValueEntity();
            copy.setWeapon(variant);
            copy.setWeaponQuality(quality.getWeaponQuality());
            copy.setValue(quality.getWeaponQuality().getName() == WeaponQualityType.SHIELD
                    ? String.valueOf(shieldValue)
                    : quality.getValue());
            variant.getWeaponQualities().add(copy);
        }
        return weaponRepository.save(variant);
    }
}
