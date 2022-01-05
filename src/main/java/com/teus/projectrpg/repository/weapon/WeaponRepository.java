package com.teus.projectrpg.repository.weapon;

import com.teus.projectrpg.entity.weapon.WeaponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<WeaponEntity, Long> {
}