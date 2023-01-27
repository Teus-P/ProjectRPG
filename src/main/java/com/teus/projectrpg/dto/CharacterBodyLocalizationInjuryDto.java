package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.type.injury.InjuryType;
import lombok.Data;

@Data
public class CharacterBodyLocalizationInjuryDto {

    private Long id;
    private BaseDto<InjuryType, InjuryEntity> injury;
    private int value;

    public CharacterBodyLocalizationInjuryDto() {
    }
}
