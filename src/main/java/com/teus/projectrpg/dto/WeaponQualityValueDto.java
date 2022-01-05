package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.weapon.WeaponQualityType;
import lombok.Data;

@Data
public class WeaponQualityValueDto {

    private Long id;
    private WeaponQualityType weaponQualityType;
    private int value;
}
