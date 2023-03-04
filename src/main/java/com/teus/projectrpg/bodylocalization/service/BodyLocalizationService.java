package com.teus.projectrpg.bodylocalization.service;

import com.teus.projectrpg.armor.entity.BodyLocalizationEntity;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;

import java.util.List;

public interface BodyLocalizationService {

    List<BodyLocalizationEntity> findAll();

    void save(BodyLocalizationEntity bodyLocalizationEntity);

    BodyLocalizationEntity findByName(BodyLocalizationType bodyLocalizationType);
}
