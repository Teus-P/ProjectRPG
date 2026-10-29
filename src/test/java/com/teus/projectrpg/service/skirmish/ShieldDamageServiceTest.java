package com.teus.projectrpg.service.skirmish;

import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.entity.CharacterWeaponEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.skirmish.dto.ShieldChangeDto;
import com.teus.projectrpg.skirmish.service.ShieldDamageService;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.entity.WeaponQualityEntity;
import com.teus.projectrpg.weapon.entity.WeaponQualityValueEntity;
import com.teus.projectrpg.weapon.repository.WeaponRepository;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShieldDamageServiceTest {

    private WeaponRepository weaponRepository;
    private ShieldDamageService service;
    private SkirmishCharacterEntity target;

    @BeforeEach
    void setUp() {
        weaponRepository = Mockito.mock(WeaponRepository.class);
        Mockito.when(weaponRepository.findMaxId()).thenReturn(5010);
        Mockito.when(weaponRepository.save(Mockito.any(WeaponEntity.class))).thenAnswer(call -> call.getArgument(0));
        service = new ShieldDamageService(weaponRepository);

        target = new SkirmishCharacterEntity();
        target.setCharacter(new CharacterEntity());
        target.getCharacter().setWeapons(new ArrayList<>());
    }

    @Test
    void shieldValue_readsTheShieldQualityOfTheChosenWeapon() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "2");
        give(shield, 1);

        assertEquals(2, service.shieldValue(target, 5L));
    }

    @Test
    void describe_doesNotChangeTheEquipment() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "2");
        give(shield, 1);

        ShieldChangeDto change = service.describe(target, 5L, 1);

        assertEquals(new ShieldChangeDto("Tarcza", 2, 1), change);
        assertEquals(shield, target.getCharacter().getWeapons().getFirst().getWeapon());
        Mockito.verify(weaponRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void apply_replacesTheShieldWithACopyWithALowerValue() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "2");
        shield.setDamage(2);
        give(shield, 1);

        ShieldChangeDto change = service.apply(target, 5L, 1);

        assertEquals(new ShieldChangeDto("Tarcza", 2, 1), change);
        List<CharacterWeaponEntity> weapons = target.getCharacter().getWeapons();
        assertEquals(1, weapons.size());
        WeaponEntity variant = weapons.getFirst().getWeapon();
        assertEquals("SHIELD_SHIELD_1", variant.getName());
        assertEquals("Tarcza (Uszkodzona)", variant.getNameTranslation());
        assertEquals(5011L, variant.getId());
        assertTrue(variant.getIsDamagedVariant());
        assertFalse(variant.getIsBaseWeapon());
        assertEquals(2, variant.getDamage());
        assertEquals("1", variant.getWeaponQualities().getFirst().getValue());
        assertFalse(shield.getIsDamagedVariant());
        assertEquals("2", shield.getWeaponQualities().getFirst().getValue());
    }

    @Test
    void apply_keepsOtherQualitiesOfTheShield() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "3");
        shield.getWeaponQualities().add(quality(shield, WeaponQualityType.DEFENSIVE, "1"));
        give(shield, 1);

        service.apply(target, 5L, 1);

        List<WeaponQualityValueEntity> qualities = target.getCharacter().getWeapons().getFirst()
                .getWeapon().getWeaponQualities();
        assertEquals(2, qualities.size());
        assertEquals("2", qualities.get(0).getValue());
        assertEquals(WeaponQualityType.DEFENSIVE, qualities.get(1).getWeaponQuality().getName());
    }

    @Test
    void apply_takesOnlyOnePieceWhenTheTargetHasSeveral() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "2");
        give(shield, 2);

        service.apply(target, 5L, 1);

        List<CharacterWeaponEntity> weapons = target.getCharacter().getWeapons();
        assertEquals(2, weapons.size());
        assertEquals(shield, weapons.get(0).getWeapon());
        assertEquals(1, weapons.get(0).getValue());
        assertEquals("SHIELD_SHIELD_1", weapons.get(1).getWeapon().getName());
        assertEquals(1, weapons.get(1).getValue());
    }

    @Test
    void apply_reusesAnExistingVariantAndAddsAPiece() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "2");
        WeaponEntity variant = shield(5001L, "SHIELD_SHIELD_1", "Tarcza (Uszkodzona)", "1");
        variant.setIsDamagedVariant(true);
        Mockito.when(weaponRepository.findWeaponEntityByName("SHIELD_SHIELD_1")).thenReturn(variant);
        give(shield, 1);
        give(variant, 1);

        service.apply(target, 5L, 1);

        List<CharacterWeaponEntity> weapons = target.getCharacter().getWeapons();
        assertEquals(1, weapons.size());
        assertEquals(variant, weapons.getFirst().getWeapon());
        assertEquals(2, weapons.getFirst().getValue());
        Mockito.verify(weaponRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void apply_damagingAVariantCreatesTheNextOneFromTheOriginal() {
        WeaponEntity original = shield(5L, "SHIELD_LARGE", "Tarcza (duża)", "3");
        WeaponEntity variant = shield(5001L, "SHIELD_LARGE_SHIELD_2", "Tarcza (duża) (Uszkodzona)", "2");
        variant.setIsDamagedVariant(true);
        Mockito.when(weaponRepository.findWeaponEntityByName("SHIELD_LARGE")).thenReturn(original);
        give(variant, 1);

        ShieldChangeDto change = service.apply(target, 5001L, 1);

        assertEquals(new ShieldChangeDto("Tarcza (duża)", 2, 1), change);
        assertEquals("SHIELD_LARGE_SHIELD_1", target.getCharacter().getWeapons().getFirst().getWeapon().getName());
    }

    @Test
    void apply_removesTheShieldWhenItsValueDropsToZero() {
        WeaponEntity shield = shield(5L, "SHIELD_BUCKLER", "Tarcza (puklerz)", "1");
        give(shield, 1);

        ShieldChangeDto change = service.apply(target, 5L, 1);

        assertEquals(new ShieldChangeDto("Tarcza (puklerz)", 1, 0), change);
        assertTrue(target.getCharacter().getWeapons().isEmpty());
        Mockito.verify(weaponRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void apply_rejectsAWeaponTheTargetDoesNotHaveOrThatIsNotAShield() {
        WeaponEntity sword = shield(7L, "SWORD", "Miecz", "1");
        sword.getWeaponQualities().clear();
        give(sword, 1);

        assertThrows(ResponseStatusException.class, () -> service.apply(target, 99L, 1));
        assertThrows(ResponseStatusException.class, () -> service.apply(target, 7L, 1));
    }

    @Test
    void apply_furtherDamageIsNumberedBecauseTranslationsMustBeUnique() {
        WeaponEntity original = shield(5L, "SHIELD_LARGE", "Tarcza (duża)", "3");
        WeaponEntity variant = shield(5001L, "SHIELD_LARGE_SHIELD_2", "Tarcza (duża) (Uszkodzona)", "2");
        variant.setIsDamagedVariant(true);
        Mockito.when(weaponRepository.findWeaponEntityByName("SHIELD_LARGE")).thenReturn(original);
        give(variant, 1);

        service.apply(target, 5001L, 1);

        assertEquals("Tarcza (duża) (Uszkodzona 2)",
                target.getCharacter().getWeapons().getFirst().getWeapon().getNameTranslation());
    }

    @Test
    void apply_canTakeSeveralPointsAtOnceButNotBelowZero() {
        WeaponEntity shield = shield(5L, "SHIELD_LARGE", "Tarcza (duża)", "3");
        give(shield, 1);

        ShieldChangeDto change = service.apply(target, 5L, 2);

        assertEquals(new ShieldChangeDto("Tarcza (duża)", 3, 1), change);
        assertEquals("SHIELD_LARGE_SHIELD_1", target.getCharacter().getWeapons().getFirst().getWeapon().getName());

        ShieldChangeDto destroyed = service.apply(target, target.getCharacter().getWeapons().getFirst()
                .getWeapon().getId(), 5);
        assertEquals(0, destroyed.after());
        assertTrue(target.getCharacter().getWeapons().isEmpty());
    }

    @Test
    void apply_renamesAnExistingVariantThatHasAnOldTranslation() {
        WeaponEntity shield = shield(5L, "SHIELD", "Tarcza", "2");
        WeaponEntity variant = shield(5001L, "SHIELD_SHIELD_1", "Tarcza (Tarcza 1)", "1");
        variant.setIsDamagedVariant(true);
        Mockito.when(weaponRepository.findWeaponEntityByName("SHIELD_SHIELD_1")).thenReturn(variant);
        give(shield, 1);

        service.apply(target, 5L, 1);

        assertEquals("Tarcza (Uszkodzona)", variant.getNameTranslation());
        Mockito.verify(weaponRepository).save(variant);
    }

    private void give(WeaponEntity weapon, int pieces) {
        CharacterWeaponEntity owned = new CharacterWeaponEntity();
        owned.setCharacter(target.getCharacter());
        owned.setWeapon(weapon);
        owned.setValue(pieces);
        target.getCharacter().getWeapons().add(owned);
    }

    private WeaponEntity shield(Long id, String name, String nameTranslation, String shieldValue) {
        WeaponEntity weapon = new WeaponEntity();
        weapon.setId(id);
        weapon.setName(name);
        weapon.setNameTranslation(nameTranslation);
        weapon.setIsBaseWeapon(true);
        weapon.setIsDamagedVariant(false);
        weapon.setWeaponQualities(new ArrayList<>(List.of(quality(weapon, WeaponQualityType.SHIELD, shieldValue))));
        return weapon;
    }

    private WeaponQualityValueEntity quality(WeaponEntity weapon, WeaponQualityType type, String value) {
        WeaponQualityEntity qualityEntity = new WeaponQualityEntity();
        qualityEntity.setName(type);
        WeaponQualityValueEntity quality = new WeaponQualityValueEntity();
        quality.setWeapon(weapon);
        quality.setWeaponQuality(qualityEntity);
        quality.setValue(value);
        return quality;
    }
}
