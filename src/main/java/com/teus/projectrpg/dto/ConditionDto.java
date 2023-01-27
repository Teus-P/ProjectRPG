package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConditionDto extends BaseDto<ConditionType> {

    Boolean hasCounter;

    public ConditionDto() {
    }

    public ConditionDto(ConditionType conditionType) {
        this.name = conditionType;
    }
}
