package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.character.CharacterSkillEntity;
import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.type.skill.SkillType;
import lombok.Data;

@Data
public class CharacterSkillDto {
    private Long id;
    private BaseDto<SkillType, SkillEntity> skill;
    private int value;

    public CharacterSkillDto() {}

    public CharacterSkillDto(CharacterSkillEntity characterSkillEntity) {
        this.id = characterSkillEntity.getId();
        this.skill = new BaseDto<>(characterSkillEntity.getSkill());
        this.value = characterSkillEntity.getValue();
    }
}
