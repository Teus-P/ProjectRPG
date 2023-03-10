package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;

import java.util.List;

public interface SkirmishCharacterService {

    SkirmishCharacterEntity findById(Long id);

    List<SkirmishCharacterEntity> findAll();

    List<SkirmishCharacterDto> sortByInitiative();

    SkirmishCharacterDto save(SkirmishCharacterDto newSkirmishCharacter);

    List<SkirmishCharacterDto> saveAll(List<SkirmishCharacterDto> skirmishCharacterDtos);

    void deleteById(Long id);

    void deleteAll();
}
