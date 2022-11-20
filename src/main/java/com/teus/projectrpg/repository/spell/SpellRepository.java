package com.teus.projectrpg.repository.spell;

import com.teus.projectrpg.entity.spell.SpellEntity;
import com.teus.projectrpg.type.spell.SpellType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellRepository extends JpaRepository <SpellEntity, Long> {

    SpellEntity findSpellEntityByName(SpellType spellType);
}
