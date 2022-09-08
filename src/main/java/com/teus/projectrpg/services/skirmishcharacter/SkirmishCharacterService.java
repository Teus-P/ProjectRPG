package com.teus.projectrpg.services.skirmishcharacter;

import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;

import java.util.List;

public interface SkirmishCharacterService {

    List<SkirmishCharacterEntity> findAll();

    SkirmishCharacterEntity save(SkirmishCharacterEntity skirmishCharacterEntity);

    void deleteById(Long id);

    void deleteAll();
}
