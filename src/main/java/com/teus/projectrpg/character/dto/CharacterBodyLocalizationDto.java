package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
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
