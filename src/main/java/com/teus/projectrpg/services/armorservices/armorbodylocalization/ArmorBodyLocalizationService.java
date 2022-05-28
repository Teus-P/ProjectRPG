package com.teus.projectrpg.services.armorservices.armorbodylocalization;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;

public interface ArmorBodyLocalizationService {

    ArmorBodyLocalizationEntity mapToEntity(ArmorBodyLocalizationDto armorBodyLocalizationDto);
}
