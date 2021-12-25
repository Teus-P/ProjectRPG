package com.teus.projectrpg.services.bodylocalization;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;

import java.util.List;

public interface BodyLocalizationService {

    List<BodyLocalizationEntity> findAll();

    void save(BodyLocalizationEntity bodyLocalizationEntity);

    BodyLocalizationEntity findByType(BodyLocalizationType bodyLocalizationType);

}
