package com.teus.projectrpg.spell.service;

import com.teus.projectrpg.spell.entity.SpellGroupEntity;
import com.teus.projectrpg.spell.type.SpellGroupType;

import java.util.List;

public interface SpellGroupService {

    List<SpellGroupEntity> findAll();

    SpellGroupEntity findByName(SpellGroupType spellGroupType);
}
