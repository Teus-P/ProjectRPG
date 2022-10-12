package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.character.*;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.note.NoteEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CharacterDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String group;
    private Boolean isRightHanded;
    private List<CharacterCharacteristicDto> characteristics;
    private List<CharacterSkillDto> skills;
    private List<CharacterTalentDto> talents;
    private List<CharacterWeaponDto> weapons;
    private List<ArmorDto> armors;
    private List<CharacterBodyLocalizationDto> bodyLocalizations;
    private List<CharacterConditionDto> conditions;
    private List<String> notes;

    public CharacterDto() {
    }

    public CharacterDto(CharacterEntity characterEntity) {
        this.id = characterEntity.getId();
        this.name = characterEntity.getName();
        this.description = characterEntity.getDescription();
        this.group = characterEntity.getGroupColumn();
        this.isRightHanded = characterEntity.getIsRightHanded();

        this.characteristics = new ArrayList<>();
        for (CharacterCharacteristicEntity characterCharacteristicEntity : characterEntity.getCharacteristics()) {
            this.characteristics.add(new CharacterCharacteristicDto(characterCharacteristicEntity));
        }

        this.skills = new ArrayList<>();
        for (CharacterSkillEntity characterSkillEntity : characterEntity.getSkills()) {
            this.skills.add(new CharacterSkillDto(characterSkillEntity));
        }

        this.talents = new ArrayList<>();
        for (CharacterTalentEntity characterTalentEntity : characterEntity.getTalents()) {
            this.talents.add(new CharacterTalentDto(characterTalentEntity));
        }

        this.weapons = new ArrayList<>();
        for (CharacterWeaponEntity characterWeaponEntity : characterEntity.getWeapons()) {
            this.weapons.add(new CharacterWeaponDto(characterWeaponEntity));
        }

        this.armors = new ArrayList<>();
        for (ArmorEntity armorEntity : characterEntity.getArmors()) {
            this.armors.add(new ArmorDto(armorEntity));
        }

        this.bodyLocalizations = new ArrayList<>();
        for(CharacterBodyLocalizationEntity bodyLocalizationEntity : characterEntity.getBodyLocalizations()) {
            this.bodyLocalizations.add(new CharacterBodyLocalizationDto(bodyLocalizationEntity));
        }

        this.conditions = new ArrayList<>();
        for (CharacterConditionEntity conditionEntity : characterEntity.getConditions()) {
            this.conditions.add(new CharacterConditionDto(conditionEntity));
        }

        this.notes = new ArrayList<>();
        for (NoteEntity noteEntity : characterEntity.getNotes()) {
            this.notes.add(noteEntity.getNote());
        }

    }
}
