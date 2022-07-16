package com.teus.projectrpg.repository.bodylocalization;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyLocalizationRepository extends JpaRepository<BodyLocalizationEntity, Long> {

    BodyLocalizationEntity findBodyLocalizationByName(BodyLocalizationType bodyLocalization);
}