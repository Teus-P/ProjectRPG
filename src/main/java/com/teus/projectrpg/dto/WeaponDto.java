package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.weapon.WeaponGroupType;
import com.teus.projectrpg.type.weapon.WeaponType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeaponDto implements Serializable {

    private Long id;
    private String name;
    private String nameTranslation;
    private WeaponType weaponType;
    private WeaponGroupType weaponGroupType;
    private String weaponRange;
    private Boolean isUsingStrength;
    private int damage;
    private List<WeaponQualityValueDto> weaponQualities;


}
