package com.teus.projectrpg.skirmish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.type.ConditionType;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.Data;

@Data
public class TestDto {

    private SkirmishCharacterDto skirmishCharacter;
    private SkillType skillType;
    private ConditionDto condition;
    private int modifier;
    private int result;
    @JsonProperty(value = "isFeasible")
    private boolean isFeasible;

    public TestDto() {
    }

    public TestDto(SkirmishCharacterDto skirmishCharacter, SkillType skillType, ConditionType conditionType, int modifier, int result, boolean isFeasible) {
        this.skirmishCharacter = skirmishCharacter;
        this.skillType = skillType;
        this.condition = new ConditionDto(conditionType);
        this.modifier = modifier;
        this.result = result;
        this.isFeasible = isFeasible;
    }
}
