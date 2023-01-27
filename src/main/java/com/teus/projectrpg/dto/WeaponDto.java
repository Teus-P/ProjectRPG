package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.weapon.WeaponGroupEntity;
import com.teus.projectrpg.entity.weapon.WeaponReachEntity;
import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import com.teus.projectrpg.type.weapon.WeaponReachType;
import com.teus.projectrpg.type.weapon.WeaponType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeaponDto implements Serializable {

    private Long id;
    private String name;
    private String nameTranslation;
    private BaseDto<WeaponType, WeaponTypeEntity> weaponType;
    private BaseDto<WeaponGroupType, WeaponGroupEntity> weaponGroup;
    private BaseDto<WeaponReachType, WeaponReachEntity> weaponReach;
    private float weaponRange;
    private Boolean isUsingStrength;
    private Boolean isUsingStrengthInRange;
    private int damage;
    private List<WeaponQualityValueDto> weaponQualities;

    public WeaponDto() {
    }
}
