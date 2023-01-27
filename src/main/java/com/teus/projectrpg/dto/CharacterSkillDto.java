package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.skill.SkillType;
import lombok.Data;

@Data
public class CharacterSkillDto {
    private Long id;
    private BaseDto<SkillType> skill;
    private int value;

    public CharacterSkillDto() {
    }
}
