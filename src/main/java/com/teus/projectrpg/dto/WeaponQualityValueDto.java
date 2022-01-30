package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.weapon.WeaponQualityValueEntity;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import lombok.Data;

@Data
public class WeaponQualityValueDto {

    private Long id;
    private WeaponQualityType name;
    private int value;

    public WeaponQualityValueDto() {

    }

    public WeaponQualityValueDto(WeaponQualityValueEntity weaponQualityValueEntity) {
        this.id = weaponQualityValueEntity.getId();
        this.name = weaponQualityValueEntity.getWeaponQuality().getName();
        this.value = weaponQualityValueEntity.getValue();
    }
}
