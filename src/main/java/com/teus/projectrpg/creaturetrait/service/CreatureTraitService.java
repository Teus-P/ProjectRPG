package com.teus.projectrpg.creaturetrait.service;

import com.teus.projectrpg.creaturetrait.entity.CreatureTraitEntity;
import com.teus.projectrpg.creaturetrait.type.CreatureTraitType;

import java.util.List;

public interface CreatureTraitService {

    List<CreatureTraitEntity> findAll();

    CreatureTraitEntity findByName(CreatureTraitType trait);
}
