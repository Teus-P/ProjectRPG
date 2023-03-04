package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.Data;

@Data
public class CharacterSkillDto {
    private Long id;
    private BaseDto<SkillType> skill;
    private int value;
}
