package com.teus.projectrpg.armor.repository;

import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorBodyLocalizationRepository extends JpaRepository<CharacterArmorBodyLocalizationEntity, Long> {
}
