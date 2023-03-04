package com.teus.projectrpg.weapon.repository;

import com.teus.projectrpg.weapon.entity.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<WeaponEntity, Long> {
    WeaponEntity findWeaponEntityByName(String name);
}
