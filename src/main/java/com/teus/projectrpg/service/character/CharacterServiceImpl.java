package com.teus.projectrpg.service.character;

import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.repository.character.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

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
