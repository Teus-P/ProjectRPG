package com.teus.projectrpg.weapon.repository;

import com.teus.projectrpg.weapon.entity.WeaponGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponGroupRepository extends JpaRepository<WeaponGroupEntity, Long> {
}