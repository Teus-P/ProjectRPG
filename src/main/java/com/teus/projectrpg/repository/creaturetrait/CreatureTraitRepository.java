package com.teus.projectrpg.repository.creaturetrait;

import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatureTraitRepository extends JpaRepository<CreatureTraitEntity, Long> {
    CreatureTraitEntity findCreatureTraitEntityByName(CreatureTraitType creatureTraitType);
}
