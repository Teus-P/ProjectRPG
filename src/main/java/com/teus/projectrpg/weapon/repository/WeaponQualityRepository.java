package com.teus.projectrpg.weapon.repository;

import com.teus.projectrpg.weapon.entity.WeaponQualityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponQualityRepository extends JpaRepository<WeaponQualityEntity, Long> {
}