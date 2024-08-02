package com.teus.projectrpg.weapon.repository;

import com.teus.projectrpg.weapon.entity.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeaponRepository extends JpaRepository<WeaponEntity, Long> {
    WeaponEntity findWeaponEntityByName(String name);

    @Query("SELECT MAX(id) FROM WeaponEntity")
    int findMaxId();
}
