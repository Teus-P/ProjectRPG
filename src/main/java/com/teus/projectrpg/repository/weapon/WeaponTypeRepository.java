package com.teus.projectrpg.repository.weapon;

import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponTypeRepository extends JpaRepository<WeaponTypeEntity, Long> {
}