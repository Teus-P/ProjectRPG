package com.teus.projectrpg.armor.repository;

import com.teus.projectrpg.armor.entity.ArmorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorRepository extends JpaRepository<ArmorEntity, Long> {
    ArmorEntity findArmorEntityByName(String name);
}
