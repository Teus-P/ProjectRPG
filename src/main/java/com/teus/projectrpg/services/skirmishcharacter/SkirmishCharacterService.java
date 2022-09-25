package com.teus.projectrpg.services.skirmishcharacter;

import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;

import java.util.List;

public interface SkirmishCharacterService {

    SkirmishCharacterEntity findById(Long id);

    List<SkirmishCharacterEntity> findAll();

    SkirmishCharacterEntity save(SkirmishCharacterEntity skirmishCharacterEntity);

    List<SkirmishCharacterEntity> saveAll(List<SkirmishCharacterEntity> skirmishCharacterEntity);

    void deleteById(Long id);

    void deleteAll();
}
