package com.teus.projectrpg.characteristic.repository;

import com.teus.projectrpg.characteristic.entity.CharacteristicEntity;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Long> {
    CharacteristicEntity findCharacteristicEntityByName(CharacteristicType characteristicType);
}
