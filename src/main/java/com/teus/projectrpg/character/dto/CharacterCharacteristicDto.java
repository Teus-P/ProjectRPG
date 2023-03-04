package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import lombok.Data;

@Data
public class CharacterCharacteristicDto {
    private Long id;
    private BaseDto<CharacteristicType> characteristic;
    private int value;
}
