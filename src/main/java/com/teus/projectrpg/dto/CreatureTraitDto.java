package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
import lombok.Data;

@Data
public class CreatureTraitDto extends BaseDto<CreatureTraitType, CreatureTraitEntity>{

    private Boolean hasValue;

    public CreatureTraitDto() {
    }

    public CreatureTraitDto(CreatureTraitEntity entity) {
        super(entity);
        this.hasValue = entity.getHasValue();
    }
}
