package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.weapon.WeaponQualityEntity;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import lombok.Data;

@Data
public class WeaponQualityValueDto {

    private Long id;
    private BaseDto<WeaponQualityType, WeaponQualityEntity> weaponQuality;
    private int value;

    public WeaponQualityValueDto() {
    }
}
