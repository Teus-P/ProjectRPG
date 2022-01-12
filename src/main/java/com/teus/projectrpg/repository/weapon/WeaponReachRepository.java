package com.teus.projectrpg.repository.weapon;

import com.teus.projectrpg.entity.weapon.WeaponReachEntity;
import com.teus.projectrpg.type.weapon.WeaponReachType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponReachRepository extends JpaRepository<WeaponReachEntity, Long> {
    WeaponReachEntity findWeaponReachEntityByName(WeaponReachType weaponReachType);
}