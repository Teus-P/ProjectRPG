package com.teus.projectrpg.service.character;

import com.teus.projectrpg.entity.character.CharacterEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterEntity> findAll();

    CharacterEntity save(CharacterEntity characterEntity);

    void deleteById(Long id);
}
