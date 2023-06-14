package com.teus.projectrpg.character.service;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;

import java.util.List;

public interface SkirmishCharacterService {

    SkirmishCharacterEntity findById(Long id);

    List<SkirmishCharacterEntity> findAllById(List<Long> ids);

    List<SkirmishCharacterEntity> findAll();

    List<SkirmishCharacterDto> getAllSortedByInitiative();

    SkirmishCharacterDto save(SkirmishCharacterDto newSkirmishCharacter);

    List<SkirmishCharacterDto> saveAllDtos(List<SkirmishCharacterDto> skirmishCharacterDtos);

    List<SkirmishCharacterDto> saveAllEntities(List<SkirmishCharacterEntity> skirmishCharacterEntities);

    void deleteById(Long id);

    void deleteAll();
}
