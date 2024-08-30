package com.teus.projectrpg.armor.repository;

import com.teus.projectrpg.armor.entity.ArmorTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorTypeRepository extends JpaRepository<ArmorTypeEntity, Long> {
}
