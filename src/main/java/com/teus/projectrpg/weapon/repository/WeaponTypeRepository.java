package com.teus.projectrpg.weapon.repository;

import com.teus.projectrpg.weapon.entity.WeaponTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponTypeRepository extends JpaRepository<WeaponTypeEntity, Long> {
}