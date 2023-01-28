package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.armor.BodyLocalizationType;
import lombok.Data;

import java.util.List;

@Data
public class CharacterBodyLocalizationDto {
    private Long id;
    private Long characterId;
    private BaseDto<BodyLocalizationType> bodyLocalization;
    private int armorPoints;
    private int additionalArmorPoints;
    private List<CharacterBodyLocalizationInjuryDto> injuries;
}
