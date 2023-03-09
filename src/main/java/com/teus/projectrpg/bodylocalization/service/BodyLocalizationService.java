package com.teus.projectrpg.bodylocalization.service;

import com.teus.projectrpg.armor.entity.BodyLocalizationEntity;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;

import java.util.List;

public interface BodyLocalizationService {

    List<BaseDto<BodyLocalizationType>> findAll();

    void save(BodyLocalizationEntity bodyLocalizationEntity);

    BodyLocalizationEntity findByName(BodyLocalizationType bodyLocalizationType);
}
