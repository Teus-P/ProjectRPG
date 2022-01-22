package com.teus.projectrpg.services.weaponservices.weapon;

import com.teus.projectrpg.entity.weapon.WeaponEntity;

import java.util.List;

public interface WeaponService {

    List<WeaponEntity> findAll();

    WeaponEntity save(WeaponEntity weaponEntity);

    WeaponEntity findByName(String name);
}
