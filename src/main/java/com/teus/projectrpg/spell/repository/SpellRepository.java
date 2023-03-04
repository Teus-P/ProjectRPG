package com.teus.projectrpg.spell.repository;

import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.spell.type.SpellType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellRepository extends JpaRepository <SpellEntity, Long> {
    SpellEntity findSpellEntityByName(SpellType spellType);
}
