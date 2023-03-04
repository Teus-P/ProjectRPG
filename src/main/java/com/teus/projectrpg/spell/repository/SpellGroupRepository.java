package com.teus.projectrpg.spell.repository;

import com.teus.projectrpg.spell.entity.SpellGroupEntity;
import com.teus.projectrpg.spell.type.SpellGroupType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellGroupRepository extends JpaRepository<SpellGroupEntity, SpellGroupType> {
    SpellGroupEntity findSpellGroupEntityByName(SpellGroupType spellGroupType);
}
