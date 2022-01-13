package com.teus.projectrpg.repository.armor;

import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorPenaltyRepository extends JpaRepository<ArmorPenaltyEntity, Long> {
    ArmorPenaltyEntity findArmorPenaltyByName(ArmorPenaltyType armorPenaltyType);
}