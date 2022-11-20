package com.teus.projectrpg.repository.spell;

import com.teus.projectrpg.entity.spell.SpellGroupEntity;
import com.teus.projectrpg.type.spell.SpellGroupType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellGroupRepository extends JpaRepository<SpellGroupEntity, SpellGroupType> {

    SpellGroupEntity findSpellGroupEntityByName(SpellGroupType spellGroupType);
}
