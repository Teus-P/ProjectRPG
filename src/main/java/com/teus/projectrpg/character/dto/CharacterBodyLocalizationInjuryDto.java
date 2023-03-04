package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.injury.type.InjuryType;
import lombok.Data;

@Data
public class CharacterBodyLocalizationInjuryDto {

    private Long id;
    private BaseDto<InjuryType> injury;
    private int value;
}
