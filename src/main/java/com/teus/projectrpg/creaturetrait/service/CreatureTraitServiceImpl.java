package com.teus.projectrpg.creaturetrait.service;

import com.teus.projectrpg.creaturetrait.dto.CreatureTraitDto;
import com.teus.projectrpg.creaturetrait.entity.CreatureTraitEntity;
import com.teus.projectrpg.creaturetrait.mapper.CreatureTraitMapper;
import com.teus.projectrpg.creaturetrait.repository.CreatureTraitRepository;
import com.teus.projectrpg.creaturetrait.type.CreatureTraitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatureTraitServiceImpl implements CreatureTraitService {

    private final CreatureTraitRepository creatureTraitRepository;
    private final CreatureTraitMapper creatureTraitMapper;

    @Override
    public List<CreatureTraitDto> findAll() {
        return creatureTraitMapper.toDtos(creatureTraitRepository.findAll());
    }

    @Override
    public CreatureTraitEntity findByName(CreatureTraitType trait) {
        return creatureTraitRepository.findCreatureTraitEntityByName(trait);
    }
}
