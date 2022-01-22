package com.teus.projectrpg.services.characteristic;

import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.repository.characteristic.CharacteristicRepository;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicServiceImpl implements CharacteristicService{

    private final CharacteristicRepository characteristicRepository;

    @Autowired
    public CharacteristicServiceImpl(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }

    @Override
    public CharacteristicEntity findByName(CharacteristicType characteristic) {
        return characteristicRepository.findCharacteristicEntityByName(characteristic);
    }
}
