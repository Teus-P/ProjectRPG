package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.type.condition.ConditionType;
import lombok.Data;

@Data
public class ConditionDto extends BaseDto<ConditionType, ConditionEntity>{

    Boolean hasCounter;

    public ConditionDto() {
    }

    public ConditionDto(ConditionEntity condition) {
        super(condition);
        this.hasCounter = condition.getHasCounter();
    }
}
