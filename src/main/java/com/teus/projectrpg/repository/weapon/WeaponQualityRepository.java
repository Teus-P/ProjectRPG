package com.teus.projectrpg.repository.weapon;

import com.teus.projectrpg.entity.weapon.WeaponQualityEntity;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponQualityRepository extends JpaRepository<WeaponQualityEntity, Long> {
    WeaponQualityEntity findWeaponQualityEntityByName(WeaponQualityType weaponQualityType);
}