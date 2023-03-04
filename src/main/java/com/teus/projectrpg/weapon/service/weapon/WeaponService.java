package com.teus.projectrpg.weapon.service.weapon;

import com.teus.projectrpg.weapon.entity.WeaponEntity;

import java.util.List;

public interface WeaponService {

    List<WeaponEntity> findAll();

    WeaponEntity save(WeaponEntity weaponEntity);

    WeaponEntity findByName(String name);

    void deleteById(Long id);
}
