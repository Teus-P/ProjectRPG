package com.teus.projectrpg.service.character;

import com.teus.projectrpg.dto.CharacterDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterEntity> findAll();

    CharacterEntity save(CharacterEntity characterEntity);

    void deleteById(Long id);

    int calculateArmorPointsForBodyLocalization(CharacterBodyLocalizationEntity bodyLocalization, List<ArmorEntity> armors);

    void createCharacterFromDto(CharacterDto newCharacter, CharacterEntity characterEntity);
}
