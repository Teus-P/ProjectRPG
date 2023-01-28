package com.teus.projectrpg.repository.armor;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorCategoryRepository extends JpaRepository<ArmorCategoryEntity, Long> {
}