package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatureTraitDto extends BaseDto<CreatureTraitType, CreatureTraitEntity> {

    private Boolean hasValue;

    public CreatureTraitDto() {
    }
}
