package com.teus.projectrpg.spell.service;

import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.spell.type.SpellType;

import java.util.List;

public interface SpellService {

    List<SpellEntity> findAll();

    SpellEntity findByName(SpellType spellType);
}
