package com.teus.projectrpg.repository;

import com.teus.projectrpg.entity.BodyLocalizationEntity;
import com.teus.projectrpg.type.BodyLocalizationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyLocalizationRepository extends JpaRepository<BodyLocalizationEntity, Long> {

    BodyLocalizationEntity findBodyLocalizationByBodyLocalization(BodyLocalizationType bodyLocalization);
}