package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.characteristic.CharacteristicType;
import lombok.Data;

@Data
public class CharacterCharacteristicDto {
    private Long id;
    private BaseDto<CharacteristicType> characteristic;
    private int value;

    public CharacterCharacteristicDto() {
    }
}
