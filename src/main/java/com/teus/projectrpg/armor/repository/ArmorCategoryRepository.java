package com.teus.projectrpg.armor.repository;

import com.teus.projectrpg.armor.entity.ArmorCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorCategoryRepository extends JpaRepository<ArmorCategoryEntity, Long> {
}