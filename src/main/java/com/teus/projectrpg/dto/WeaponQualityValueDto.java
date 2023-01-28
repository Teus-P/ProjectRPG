package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.weapon.WeaponQualityType;
import lombok.Data;

@Data
public class WeaponQualityValueDto {

    private Long id;
    private BaseDto<WeaponQualityType> weaponQuality;
    private int value;
}
