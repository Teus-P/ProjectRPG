package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.entity.CharacterEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterEntity> findAll();

    CharacterEntity save(CharacterEntity characterEntity);

    void deleteById(Long id);
}
