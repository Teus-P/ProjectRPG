package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.weapon.*;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import com.teus.projectrpg.type.weapon.WeaponReachType;
import com.teus.projectrpg.type.weapon.WeaponType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class WeaponDto implements Serializable {

    private Long id;
    private String name;
    private String nameTranslation;
    private BaseDto<WeaponType, WeaponTypeEntity> weaponType;
    private BaseDto<WeaponGroupType, WeaponGroupEntity> weaponGroupType;
    private BaseDto<WeaponReachType, WeaponReachEntity> weaponReach;
    private int weaponRange;
    private Boolean isUsingStrength;
    private int damage;
    private List<WeaponQualityValueDto> weaponQualities;

    public WeaponDto() {
    }

    public WeaponDto(WeaponEntity weaponEntity) {
        this.id = weaponEntity.getId();
        this.name = weaponEntity.getName();
        this.nameTranslation = weaponEntity.getNameTranslation();
        this.weaponType = new BaseDto<>(weaponEntity.getWeaponType());
        this.weaponGroupType = new BaseDto<>(weaponEntity.getWeaponGroup());
        this.weaponReach = new BaseDto<>(weaponEntity.getWeaponReach());
        this.weaponRange = weaponEntity.getWeaponRange();
        this.isUsingStrength = weaponEntity.getIsUsingStrength();
        this.damage = weaponEntity.getDamage();

        this.weaponQualities = new ArrayList<>();
        for (WeaponQualityValueEntity weaponQualityValueEntity : weaponEntity.getWeaponQualities()) {
            this.weaponQualities.add(new WeaponQualityValueDto(weaponQualityValueEntity));
        }
    }
}
