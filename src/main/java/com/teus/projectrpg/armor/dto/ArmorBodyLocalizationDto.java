package com.teus.projectrpg.armor.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.Data;

@Data
public class ArmorBodyLocalizationDto {
    private Long id;
    private BaseDto<BodyLocalizationType> bodyLocalization;
    private int armorPoints;
}
