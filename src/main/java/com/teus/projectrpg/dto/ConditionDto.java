package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ConditionDto extends BaseDto<ConditionType> {

    Boolean hasCounter;

    public ConditionDto(ConditionType conditionType) {
        this.name = conditionType;
    }
}

