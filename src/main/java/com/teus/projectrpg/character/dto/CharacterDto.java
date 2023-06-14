package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.spell.dto.SpellDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CharacterDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String group;
    private String status;
    private Boolean isRightHanded;
    private List<CharacterCharacteristicDto> characteristics;
    private List<CharacterSkillDto> skills;
    private List<CharacterTalentDto> talents;
    private List<CharacterCreatureTraitDto> traits;
    private List<SpellDto> spells;
    private List<CharacterWeaponDto> weapons;
    private List<ArmorDto> armors;
    private List<CharacterBodyLocalizationDto> bodyLocalizations;
    private List<CharacterConditionDto> conditions;
    private List<String> notes;
}
