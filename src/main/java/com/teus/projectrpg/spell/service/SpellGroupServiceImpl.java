package com.teus.projectrpg.spell.service;

import com.teus.projectrpg.spell.entity.SpellGroupEntity;
import com.teus.projectrpg.spell.repository.SpellGroupRepository;
import com.teus.projectrpg.spell.type.SpellGroupType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpellGroupServiceImpl implements SpellGroupService{

    private final SpellGroupRepository spellGroupRepository;

    @Override
    public List<SpellGroupEntity> findAll() {
        return spellGroupRepository.findAll();
    }

    @Override
    public SpellGroupEntity findByName(SpellGroupType spellGroupType) {
        return spellGroupRepository.findSpellGroupEntityByName(spellGroupType);
    }
}
