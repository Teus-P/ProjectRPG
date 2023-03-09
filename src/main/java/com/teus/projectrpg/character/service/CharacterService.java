package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.CharacterDto;

import java.util.List;

public interface CharacterService {
    List<CharacterDto> findAll();

    CharacterDto save(CharacterDto newCharacter);

    void deleteById(Long id);
}
