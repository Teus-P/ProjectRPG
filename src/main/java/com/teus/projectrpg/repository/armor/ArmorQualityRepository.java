package com.teus.projectrpg.repository.armor;

import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorQualityRepository extends JpaRepository<ArmorQualityEntity, Long> {
    ArmorQualityEntity findArmorQualityByArmorQualityType(ArmorQualityType armorQualityType);
}