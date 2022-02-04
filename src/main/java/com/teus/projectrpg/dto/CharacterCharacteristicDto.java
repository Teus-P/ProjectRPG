package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.character.CharacterCharacteristicEntity;
import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import lombok.Data;

@Data
public class CharacterCharacteristicDto {
    private Long id;
    private BaseDto<CharacteristicType, CharacteristicEntity> characteristic;
    private int value;

    public CharacterCharacteristicDto() {
    }

    public CharacterCharacteristicDto(CharacterCharacteristicEntity characterCharacteristicEntity) {
        this.id = characterCharacteristicEntity.getId();
        this.characteristic = new BaseDto<>(characterCharacteristicEntity.getCharacteristic());
        this.value = characterCharacteristicEntity.getValue();
    }
}
