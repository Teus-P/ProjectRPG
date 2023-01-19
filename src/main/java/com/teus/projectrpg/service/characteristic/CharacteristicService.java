package com.teus.projectrpg.service.characteristic;

import com.teus.projectrpg.entity.character.CharacterCharacteristicEntity;
import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.type.characteristic.CharacteristicType;

import java.util.List;

public interface CharacteristicService {
    CharacteristicEntity findByName(CharacteristicType characteristic);

    int getCharacteristicValueByType(List<CharacterCharacteristicEntity> characteristics, CharacteristicType characteristicType);
}
