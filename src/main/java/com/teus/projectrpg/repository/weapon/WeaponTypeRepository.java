package com.teus.projectrpg.repository.weapon;

import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import com.teus.projectrpg.type.weapon.WeaponType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponTypeRepository extends JpaRepository<WeaponTypeEntity, Long> {
    WeaponTypeEntity findWeaponTypeEntityByName(WeaponType weaponType);
}