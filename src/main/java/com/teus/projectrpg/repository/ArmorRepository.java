package com.teus.projectrpg.repository;

import com.teus.projectrpg.entity.armor.ArmorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorRepository extends JpaRepository<ArmorEntity, Long> {
}
