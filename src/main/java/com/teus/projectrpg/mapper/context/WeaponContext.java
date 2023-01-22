package com.teus.projectrpg.mapper.context;

import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.entity.weapon.WeaponQualityValueEntity;
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
