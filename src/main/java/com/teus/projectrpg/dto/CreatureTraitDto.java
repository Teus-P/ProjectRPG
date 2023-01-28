package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreatureTraitDto extends BaseDto<CreatureTraitType> {

    private Boolean hasValue;
}
