package com.teus.projectrpg.armor.repository;

import com.teus.projectrpg.armor.entity.ArmorPenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorPenaltyRepository extends JpaRepository<ArmorPenaltyEntity, Long> {
}