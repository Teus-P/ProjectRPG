package com.teus.projectrpg.skill.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SkillDto extends BaseDto<SkillType> {

    @JsonProperty("isSkirmishSkill")
    private boolean isSkirmishSkill;

    private boolean hasSpecialisation;
}
