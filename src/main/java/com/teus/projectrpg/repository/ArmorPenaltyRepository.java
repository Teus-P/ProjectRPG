package com.teus.projectrpg.repository;

import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.type.ArmorPenaltyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorPenaltyRepository extends JpaRepository<ArmorPenaltyEntity, Long> {
    ArmorPenaltyEntity findArmorPenaltyByArmorPenaltyType(ArmorPenaltyType armorPenaltyType);
}