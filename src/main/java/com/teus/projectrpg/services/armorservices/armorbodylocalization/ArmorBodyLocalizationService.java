package com.teus.projectrpg.services.armorservices.armorbodylocalization;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;

import java.util.List;

public interface ArmorBodyLocalizationService {

    List<ArmorBodyLocalizationEntity> findByArmor(ArmorEntity armor);

    void save(ArmorBodyLocalizationEntity armorBodyLocalization);

    ArmorBodyLocalizationEntity mapToEntity(ArmorBodyLocalizationDto armorBodyLocalizationDto);
}
