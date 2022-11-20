package com.teus.projectrpg.services.spell;

import com.teus.projectrpg.entity.spell.SpellEntity;
import com.teus.projectrpg.type.spell.SpellType;

import java.util.List;

public interface SpellService {

    List<SpellEntity> findAll();

    SpellEntity findByName(SpellType spellType);
}
