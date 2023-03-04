package com.teus.projectrpg.condition.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.condition.type.ConditionType;
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

