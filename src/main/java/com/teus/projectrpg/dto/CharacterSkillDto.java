package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.type.skill.SkillType;
import lombok.Data;

@Data
public class CharacterSkillDto {
    private Long id;
    private SkillType skill;
    private int value;

    public CharacterSkillDto() {}

    public CharacterSkillDto(CharacterSkillEntity characterSkillEntity) {
        this.id = characterSkillEntity.getId();
        this.skill = characterSkillEntity.getSkill().getName();
        this.value = characterSkillEntity.getValue();
    }
}
