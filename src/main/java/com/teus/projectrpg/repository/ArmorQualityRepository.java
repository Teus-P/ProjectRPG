package com.teus.projectrpg.repository;

import com.teus.projectrpg.entity.ArmorQualityEntity;
import com.teus.projectrpg.type.ArmorQualityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorQualityRepository extends JpaRepository<ArmorQualityEntity, Long> {
    ArmorQualityEntity findArmorQualityByArmorQualityType(ArmorQualityType armorQualityType);
}