package com.teus.projectrpg.bodylocalization.repository;

import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyLocalizationRepository extends JpaRepository<BodyLocalizationEntity, Long> {
    BodyLocalizationEntity findBodyLocalizationByName(BodyLocalizationType bodyLocalization);
}