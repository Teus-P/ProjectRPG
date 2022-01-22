package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.character.CharacterCharacteristicEntity;
import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.entity.character.CharacterTalentEntity;
import com.teus.projectrpg.entity.weapon.WeaponEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CharacterDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Boolean isRightHanded;
    private List<CharacterCharacteristicDto> characteristics;
    private List<CharacterSkillDto> skills;
    private List<CharacterTalentDto> talents;
    private List<WeaponDto> weapons;
    private List<ArmorDto> armors;

    public CharacterDto() {}

    public CharacterDto(CharacterEntity characterEntity) {
        this.id = characterEntity.getId();
        this.name = characterEntity.getName();
        this.description = characterEntity.getDescription();
        this.isRightHanded = characterEntity.getIsRightHanded();

        this.characteristics = new ArrayList<>();
        for (CharacterCharacteristicEntity characterCharacteristicEntity : characterEntity.getCharacteristics()) {
            this.characteristics.add(new CharacterCharacteristicDto(characterCharacteristicEntity));
        }

        this.skills = new ArrayList<>();
        for(CharacterSkillEntity characterSkillEntity: characterEntity.getSkills()) {
            this.skills.add(new CharacterSkillDto(characterSkillEntity));
        }

        this.talents = new ArrayList<>();
        for(CharacterTalentEntity characterTalentEntity: characterEntity.getTalents()) {
            this.talents.add(new CharacterTalentDto(characterTalentEntity));
        }

        this.weapons = new ArrayList<>();
        for(WeaponEntity weaponEntity: characterEntity.getWeapons()) {
            this.weapons.add(new WeaponDto(weaponEntity));
        }

        this.armors = new ArrayList<>();
        for(ArmorEntity armorEntity: characterEntity.getArmors()) {
            this.armors.add(new ArmorDto(armorEntity));
        }
    }
}
