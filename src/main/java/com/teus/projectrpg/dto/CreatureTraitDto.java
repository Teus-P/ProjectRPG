package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatureTraitDto extends BaseDto<CreatureTraitType> {

    private Boolean hasValue;

    public CreatureTraitDto() {
    }
}
