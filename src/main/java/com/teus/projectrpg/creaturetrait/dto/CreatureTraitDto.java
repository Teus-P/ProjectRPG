package com.teus.projectrpg.creaturetrait.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.creaturetrait.type.CreatureTraitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreatureTraitDto extends BaseDto<CreatureTraitType> {

    private Boolean hasValue;
}
