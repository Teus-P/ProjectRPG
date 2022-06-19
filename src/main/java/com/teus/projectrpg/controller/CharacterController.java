package com.teus.projectrpg.controller;

import com.teus.projectrpg.controller.exception.ElementNotFoundException;
import com.teus.projectrpg.controller.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.*;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.character.*;
import com.teus.projectrpg.services.armorservices.armor.ArmorService;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import com.teus.projectrpg.services.character.CharacterService;
import com.teus.projectrpg.services.characteristic.CharacteristicService;
import com.teus.projectrpg.services.skill.SkillService;
import com.teus.projectrpg.services.talent.TalentService;
import com.teus.projectrpg.services.weaponservices.weapon.WeaponService;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CharacterController {

    private final CharacterService characterService;
    private final CharacteristicService characteristicService;
    private final SkillService skillService;
    private final TalentService talentService;
    private final WeaponService weaponService;
    private final ArmorService armorService;
    private final BodyLocalizationService bodyLocalizationService;

    public CharacterController(CharacterService characterService, CharacteristicService characteristicService, SkillService skillService, TalentService talentService, WeaponService weaponService, ArmorService armorService, BodyLocalizationService bodyLocalizationService) {
        this.characterService = characterService;
        this.characteristicService = characteristicService;
        this.skillService = skillService;
        this.talentService = talentService;
        this.weaponService = weaponService;
        this.armorService = armorService;
        this.bodyLocalizationService = bodyLocalizationService;
    }

    @GetMapping("/character")
    List<CharacterDto> getAllCharacters() {
        List<CharacterDto> characterDtos = new ArrayList<>();

        for (CharacterEntity characterEntity : characterService.findAll()) {
            characterDtos.add(new CharacterDto(characterEntity));
        }

        return characterDtos;
    }

    @PutMapping("/character")
    CharacterEntity putCharacter(@RequestBody CharacterDto newCharacter) {
        CharacterEntity characterEntity = new CharacterEntity();
        if (newCharacter.getId() != null) {
            characterEntity.setId(newCharacter.getId());
        }
        characterEntity.setName(newCharacter.getName());
        characterEntity.setDescription(newCharacter.getDescription());
        characterEntity.setIsRightHanded(newCharacter.getIsRightHanded());

        ArrayList<CharacterCharacteristicEntity> characterCharacteristics = new ArrayList<>();
        for (CharacterCharacteristicDto characterCharacteristicDto : newCharacter.getCharacteristics()) {
            CharacterCharacteristicEntity characterCharacteristicEntity = new CharacterCharacteristicEntity();
            characterCharacteristicEntity.setCharacter(characterEntity);
            characterCharacteristicEntity.setCharacteristic(characteristicService.findByName(characterCharacteristicDto.getCharacteristic().getName()));
            characterCharacteristicEntity.setValue(characterCharacteristicDto.getValue());
            characterCharacteristics.add(characterCharacteristicEntity);
        }
        characterEntity.setCharacteristics(characterCharacteristics);

        ArrayList<CharacterSkillEntity> characterSkillEntities = new ArrayList<>();
        for (CharacterSkillDto characterSkillDto : newCharacter.getSkills()) {
            CharacterSkillEntity characterSkillEntity = new CharacterSkillEntity();
            characterSkillEntity.setCharacter(characterEntity);
            characterSkillEntity.setSkill(skillService.findByName(characterSkillDto.getSkill().getName()));
            characterSkillEntity.setValue(characterSkillDto.getValue());
            characterSkillEntities.add(characterSkillEntity);
        }
        characterEntity.setSkills(characterSkillEntities);

        ArrayList<CharacterTalentEntity> characterTalentEntities = new ArrayList<>();
        for (CharacterTalentDto characterTalentDto : newCharacter.getTalents()) {
            CharacterTalentEntity characterTalentEntity = new CharacterTalentEntity();
            characterTalentEntity.setCharacter(characterEntity);
            characterTalentEntity.setTalent(talentService.findByName(characterTalentDto.getTalent().getName()));
            characterTalentEntity.setValue(characterTalentDto.getValue());
            characterTalentEntities.add(characterTalentEntity);
        }
        characterEntity.setTalents(characterTalentEntities);

        ArrayList<CharacterWeaponEntity> characterWeaponEntities = new ArrayList<>();
        for (CharacterWeaponDto characterWeaponDto : newCharacter.getWeapons()) {
            CharacterWeaponEntity characterWeaponEntity = new CharacterWeaponEntity();
            characterWeaponEntity.setCharacter(characterEntity);
            characterWeaponEntity.setWeapon(weaponService.findByName(characterWeaponDto.getWeapon().getName()));
            characterWeaponEntity.setValue(characterWeaponDto.getValue());
            characterWeaponEntities.add(characterWeaponEntity);
        }
        characterEntity.setWeapons(characterWeaponEntities);

        ArrayList<ArmorEntity> armorEntities = new ArrayList<>();
        for (ArmorDto armorDto : newCharacter.getArmors()) {
            armorEntities.add(armorService.findByName(armorDto.getName()));
        }
        characterEntity.setArmors(armorEntities);

        ArrayList<CharacterBodyLocalizationEntity> characterBodyLocalizationEntities = new ArrayList<>();
        for (CharacterBodyLocalizationDto characterBodyLocalizationDto : newCharacter.getBodyLocalizations()) {
            CharacterBodyLocalizationEntity characterBodyLocalizationEntity = new CharacterBodyLocalizationEntity();
            characterBodyLocalizationEntity.setCharacter(characterEntity);
            characterBodyLocalizationEntity.setBodyLocalization(bodyLocalizationService.findByName(characterBodyLocalizationDto.getBodyLocalization().getName()));
            characterBodyLocalizationEntity.setArmorPoints(
                    characterService.calculateArmorPointsForBodyLocalization(characterBodyLocalizationEntity, characterEntity.getArmors())
            );
            characterBodyLocalizationEntities.add(characterBodyLocalizationEntity);
        }
        characterEntity.setBodyLocalizations(characterBodyLocalizationEntities);

        try {
            return characterService.save(characterEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/character/{id}")
    void deleteCharacter(@PathVariable Long id) {
        try {
            characterService.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }
}
