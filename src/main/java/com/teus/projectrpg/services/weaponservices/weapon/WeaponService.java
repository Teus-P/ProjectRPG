package com.teus.projectrpg.services.weaponservices.weapon;

import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.entity.weapon.WeaponEntity;

import java.util.List;

public interface WeaponService {

    List<WeaponEntity> findAll();

    WeaponEntity save(WeaponEntity weaponEntity);

    WeaponEntity findByName(String name);

    void deleteById(Long id);

    WeaponEntity mapToEntity(WeaponDto weaponDto);
}
