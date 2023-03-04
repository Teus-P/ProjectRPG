package com.teus.projectrpg.weapon.mapper;

import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.entity.WeaponQualityValueEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import java.util.List;

public class WeaponContext {

    @AfterMapping
    public void setWeaponQualities(@MappingTarget WeaponEntity weapon) {
        List<WeaponQualityValueEntity> weaponQualityValueEntities = weapon.getWeaponQualities().stream()
                .peek(quality -> quality.setWeapon(weapon))
                .toList();

        weapon.setWeaponQualities(weaponQualityValueEntities);
    }
}
