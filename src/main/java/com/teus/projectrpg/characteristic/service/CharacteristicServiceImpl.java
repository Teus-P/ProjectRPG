package com.teus.projectrpg.characteristic.service;

import com.teus.projectrpg.character.entity.CharacterCharacteristicEntity;
import com.teus.projectrpg.characteristic.entity.CharacteristicEntity;
import com.teus.projectrpg.characteristic.repository.CharacteristicRepository;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    private final CharacteristicRepository characteristicRepository;

    @Override
    public CharacteristicEntity findByName(CharacteristicType characteristic) {
        return characteristicRepository.findCharacteristicEntityByName(characteristic);
    }

    @Override
    public int getCharacteristicValueByType(List<CharacterCharacteristicEntity> characteristics, CharacteristicType characteristicType) {
        return characteristics.stream()
                .filter(c -> c.getCharacteristic().getName().equals(characteristicType))
                .findFirst()
                .get().getValue();
    }
}
