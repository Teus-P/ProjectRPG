package com.teus.projectrpg.service.creaturetrait;

import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import com.teus.projectrpg.repository.creaturetrait.CreatureTraitRepository;
import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatureTraitServiceImpl implements CreatureTraitService{

    private final CreatureTraitRepository creatureTraitRepository;

    @Autowired
    public CreatureTraitServiceImpl(CreatureTraitRepository creatureTraitRepository) {
        this.creatureTraitRepository = creatureTraitRepository;
    }

    @Override
    public List<CreatureTraitEntity> findAll() {
        return creatureTraitRepository.findAll();
    }

    @Override
    public CreatureTraitEntity findByName(CreatureTraitType trait) {
        return creatureTraitRepository.findCreatureTraitEntityByName(trait);
    }
}
