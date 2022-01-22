package com.teus.projectrpg.repository.characteristic;

import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Long> {
    CharacteristicEntity findCharacteristicEntityByName(CharacteristicType characteristicType);
}
