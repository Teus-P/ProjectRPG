package com.teus.projectrpg.service.characteristic;

import com.teus.projectrpg.entity.character.CharacterCharacteristicEntity;
import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.repository.characteristic.CharacteristicRepository;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
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
