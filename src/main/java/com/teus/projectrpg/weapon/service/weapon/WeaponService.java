package com.teus.projectrpg.weapon.service.weapon;

import com.teus.projectrpg.weapon.dto.WeaponDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;

import java.util.List;

public interface WeaponService {

    List<WeaponDto> findAll();

    WeaponDto save(WeaponDto newWeapon);

    WeaponEntity findByName(String name);

    void deleteById(Long id);
}
