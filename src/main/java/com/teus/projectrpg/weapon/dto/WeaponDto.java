package com.teus.projectrpg.weapon.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.availability.type.AvailabilityType;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import com.teus.projectrpg.weapon.type.WeaponReachType;
import com.teus.projectrpg.weapon.type.WeaponType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeaponDto implements Serializable {

    private Long id;
    private String name;
    private String nameTranslation;
    private BaseDto<WeaponType> weaponType;
    private BaseDto<WeaponGroupType> weaponGroup;
    private BaseDto<WeaponReachType> weaponReach;
    private float weaponRange;
    private Boolean isUsingStrength;
    private Boolean isUsingStrengthInRange;
    private int damage;
    private List<WeaponQualityValueDto> weaponQualities;
    private String price;
    private String encumbrance;
    private BaseDto<AvailabilityType> availability;
    private Boolean isBaseWeapon;
}
