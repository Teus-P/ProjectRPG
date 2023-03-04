package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Override
    public List<CharacterEntity> findAll() {
        return characterRepository.findAll();
    }

    @Override
    public CharacterEntity save(CharacterEntity characterEntity) {
        return characterRepository.save(characterEntity);
    }

    @Override
    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }
}
