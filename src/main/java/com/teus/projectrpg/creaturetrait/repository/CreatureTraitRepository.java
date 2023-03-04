package com.teus.projectrpg.creaturetrait.repository;

import com.teus.projectrpg.creaturetrait.entity.CreatureTraitEntity;
import com.teus.projectrpg.creaturetrait.type.CreatureTraitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatureTraitRepository extends JpaRepository<CreatureTraitEntity, Long> {
    CreatureTraitEntity findCreatureTraitEntityByName(CreatureTraitType creatureTraitType);
}
