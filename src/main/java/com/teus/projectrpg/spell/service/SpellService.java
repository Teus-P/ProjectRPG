package com.teus.projectrpg.spell.service;

import com.teus.projectrpg.spell.dto.SpellDto;
import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.spell.type.SpellType;

import java.util.List;

public interface SpellService {

    List<SpellDto> findAll();

    SpellEntity findByName(SpellType spellType);
}
