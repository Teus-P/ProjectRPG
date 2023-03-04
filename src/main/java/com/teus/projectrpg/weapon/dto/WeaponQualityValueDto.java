package com.teus.projectrpg.weapon.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import lombok.Data;

@Data
public class WeaponQualityValueDto {

    private Long id;
    private BaseDto<WeaponQualityType> weaponQuality;
    private String value;
}
