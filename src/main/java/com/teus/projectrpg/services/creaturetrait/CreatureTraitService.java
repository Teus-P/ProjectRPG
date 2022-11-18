package com.teus.projectrpg.services.creaturetrait;

import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;

import java.util.List;

public interface CreatureTraitService {

    List<CreatureTraitEntity> findAll();

    CreatureTraitEntity findByName(CreatureTraitType trait);
}
