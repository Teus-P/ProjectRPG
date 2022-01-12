package com.teus.projectrpg.repository.weapon;

import com.teus.projectrpg.entity.weapon.WeaponGroupEntity;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponGroupRepository extends JpaRepository<WeaponGroupEntity, Long> {
    WeaponGroupEntity findWeaponGroupEntityByName(WeaponGroupType weaponGroupType);
}