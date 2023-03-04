package com.teus.projectrpg.spell.service;

import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.spell.repository.SpellRepository;
import com.teus.projectrpg.spell.type.SpellType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpellServiceImpl implements SpellService{

    private final SpellRepository spellRepository;

    @Override
    public List<SpellEntity> findAll() {
        return spellRepository.findAll();
    }

    @Override
    public SpellEntity findByName(SpellType spellType) {
        return spellRepository.findSpellEntityByName(spellType);
    }
}
