package com.teus.projectrpg.service.spell;

import com.teus.projectrpg.entity.spell.SpellGroupEntity;
import com.teus.projectrpg.type.spell.SpellGroupType;

import java.util.List;

public interface SpellGroupService {

    List<SpellGroupEntity> findAll();

    SpellGroupEntity findByName(SpellGroupType spellGroupType);
}
