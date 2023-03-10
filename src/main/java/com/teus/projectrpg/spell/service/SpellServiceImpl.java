package com.teus.projectrpg.spell.service;

import com.teus.projectrpg.spell.dto.SpellDto;
import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.spell.mapper.SpellMapper;
import com.teus.projectrpg.spell.repository.SpellRepository;
import com.teus.projectrpg.spell.type.SpellType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpellServiceImpl implements SpellService {

    private final SpellRepository spellRepository;
    private final SpellMapper spellMapper;

    @Override
    public List<SpellDto> findAll() {
        return spellMapper.toDtos(spellRepository.findAll());
    }

    @Override
    public SpellEntity findByName(SpellType spellType) {
        return spellRepository.findSpellEntityByName(spellType);
    }
}
