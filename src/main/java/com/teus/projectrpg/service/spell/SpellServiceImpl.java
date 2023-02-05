package com.teus.projectrpg.service.spell;

import com.teus.projectrpg.entity.spell.SpellEntity;
import com.teus.projectrpg.repository.spell.SpellRepository;
import com.teus.projectrpg.type.spell.SpellType;
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