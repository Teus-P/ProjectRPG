package com.teus.projectrpg.services.BodyLocalization;

import com.teus.projectrpg.entity.BodyLocalizationEntity;
import com.teus.projectrpg.type.BodyLocalizationType;

import java.util.List;

public interface BodyLocalizationService {

    List<BodyLocalizationEntity> findAll();

    void save(BodyLocalizationEntity bodyLocalizationEntity);

    BodyLocalizationEntity findByType(BodyLocalizationType bodyLocalizationType);

}
