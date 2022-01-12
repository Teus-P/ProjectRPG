package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import com.teus.projectrpg.type.weapon.WeaponType;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeaponTypeDto implements Serializable {
    private Long id;
    private WeaponType weaponType;

    public WeaponTypeDto() {
    }

    public WeaponTypeDto(WeaponType name) {
        this.weaponType = name;
    }

    public WeaponTypeDto(WeaponTypeEntity weaponType) {
        this.id = weaponType.getId();
        this.weaponType = weaponType.getName();
    }
}
