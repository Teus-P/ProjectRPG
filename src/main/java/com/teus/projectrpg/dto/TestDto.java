package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.condition.ConditionType;
import com.teus.projectrpg.type.skill.SkillType;
import lombok.Data;

@Data
public class TestDto {

    private SkirmishCharacterDto skirmishCharacter;
    private SkillType skillType;
    private ConditionType conditionType;
    private int modifier;
    private int result;

    public TestDto() {
    }

    public TestDto(SkirmishCharacterDto skirmishCharacter, SkillType skillType, ConditionType conditionType, int modifier, int result) {
        this.skirmishCharacter = skirmishCharacter;
        this.skillType = skillType;
        this.conditionType = conditionType;
        this.modifier = modifier;
        this.result = result;
    }
}
