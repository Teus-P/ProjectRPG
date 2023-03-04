package com.teus.projectrpg.characteristic.service;

import com.teus.projectrpg.character.entity.CharacterCharacteristicEntity;
import com.teus.projectrpg.characteristic.entity.CharacteristicEntity;
import com.teus.projectrpg.characteristic.type.CharacteristicType;

import java.util.List;

public interface CharacteristicService {
    CharacteristicEntity findByName(CharacteristicType characteristic);

    int getCharacteristicValueByType(List<CharacterCharacteristicEntity> characteristics, CharacteristicType characteristicType);
}
