package com.teus.projectrpg.services.characteristic;

import com.teus.projectrpg.dto.CharacterCharacteristicDto;
import com.teus.projectrpg.entity.character.CharacterCharacteristicEntity;
import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.type.characteristic.CharacteristicType;

import java.util.List;

public interface CharacteristicService {
    CharacteristicEntity findByName(CharacteristicType characteristic);

    int getCharacteristicDtoByType(List<CharacterCharacteristicEntity> characteristics, CharacteristicType characteristicType);
}
