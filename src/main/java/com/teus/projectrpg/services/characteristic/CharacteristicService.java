package com.teus.projectrpg.services.characteristic;

import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.type.characteristic.CharacteristicType;

public interface CharacteristicService {
    CharacteristicEntity findByName(CharacteristicType characteristic);
}
