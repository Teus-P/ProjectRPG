package com.teus.projectrpg.services.armorservices.armorbodylocalization;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmorBodyLocalizationServiceImpl implements ArmorBodyLocalizationService {

    private final BodyLocalizationService bodyLocalizationService;

    @Autowired
    public ArmorBodyLocalizationServiceImpl(BodyLocalizationService bodyLocalizationService) {
        this.bodyLocalizationService = bodyLocalizationService;
    }

    @Override
    public ArmorBodyLocalizationEntity mapToEntity(ArmorBodyLocalizationDto armorBodyLocalizationDto) {
        ArmorBodyLocalizationEntity armorBodyLocalizationEntity = new ArmorBodyLocalizationEntity();
        armorBodyLocalizationEntity.setId(armorBodyLocalizationDto.getId());
        armorBodyLocalizationEntity.setArmorPoints(armorBodyLocalizationDto.getArmorPoints());
        armorBodyLocalizationEntity.setBrokenArmorPoints(armorBodyLocalizationEntity.getBrokenArmorPoints());
        armorBodyLocalizationEntity.setBodyLocalization(
                bodyLocalizationService.mapToEntity(armorBodyLocalizationDto.getBodyLocalization())
        );
        return armorBodyLocalizationEntity;
    }
}
