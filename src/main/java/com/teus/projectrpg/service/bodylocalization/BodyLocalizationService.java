package com.teus.projectrpg.service.bodylocalization;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;

import java.util.List;

public interface BodyLocalizationService {

    List<BodyLocalizationEntity> findAll();

    void save(BodyLocalizationEntity bodyLocalizationEntity);

    BodyLocalizationEntity findByName(BodyLocalizationType bodyLocalizationType);

    BodyLocalizationEntity mapToEntity(BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalizationDto);
}
