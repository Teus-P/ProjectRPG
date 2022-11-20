package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.spell.SpellEntity;
import com.teus.projectrpg.entity.spell.SpellGroupEntity;
import com.teus.projectrpg.type.spell.SpellGroupType;
import com.teus.projectrpg.type.spell.SpellType;
import lombok.Data;

@Data
public class SpellDto extends BaseDto<SpellType, SpellEntity>{

    BaseDto<SpellGroupType, SpellGroupEntity> spellGroup;

    public SpellDto() {
    }

    public SpellDto(SpellEntity entity) {
        super(entity);
        this.spellGroup = new BaseDto<>(entity.getSpellGroup());
    }
}
