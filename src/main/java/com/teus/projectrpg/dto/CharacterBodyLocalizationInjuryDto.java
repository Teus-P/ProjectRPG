package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.injury.CharacterBodyLocalizationInjuryEntity;
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

    public CharacterBodyLocalizationInjuryDto(CharacterBodyLocalizationInjuryEntity injury) {
        this.id = injury.getId();
        this.injury = new BaseDto<>(injury.getInjury());
        this.value = injury.getValue();
    }
}
