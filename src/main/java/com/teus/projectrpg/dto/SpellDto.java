package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.spell.SpellGroupType;
import com.teus.projectrpg.type.spell.SpellType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpellDto extends BaseDto<SpellType> {

    BaseDto<SpellGroupType> spellGroup;
}
