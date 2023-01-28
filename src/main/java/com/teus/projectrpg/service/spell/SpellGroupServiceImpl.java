package com.teus.projectrpg.service.spell;

import com.teus.projectrpg.entity.spell.SpellGroupEntity;
import com.teus.projectrpg.repository.spell.SpellGroupRepository;
import com.teus.projectrpg.type.spell.SpellGroupType;
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
