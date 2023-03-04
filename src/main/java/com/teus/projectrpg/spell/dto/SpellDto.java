package com.teus.projectrpg.spell.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.spell.type.SpellGroupType;
import com.teus.projectrpg.spell.type.SpellType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class
SpellDto extends BaseDto<SpellType> {

    BaseDto<SpellGroupType> spellGroup;
}
