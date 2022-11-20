package com.teus.projectrpg.services.spell;

import com.teus.projectrpg.entity.spell.SpellGroupEntity;
import com.teus.projectrpg.repository.spell.SpellGroupRepository;
import com.teus.projectrpg.type.spell.SpellGroupType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpellGroupServiceImpl implements SpellGroupService{

    private final SpellGroupRepository spellGroupRepository;

    public SpellGroupServiceImpl(SpellGroupRepository spellGroupRepository) {
        this.spellGroupRepository = spellGroupRepository;
    }

    @Override
    public List<SpellGroupEntity> findAll() {
        return spellGroupRepository.findAll();
    }

    @Override
    public SpellGroupEntity findByName(SpellGroupType spellGroupType) {
        return spellGroupRepository.findSpellGroupEntityByName(spellGroupType);
    }
}
