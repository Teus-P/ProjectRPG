package com.teus.projectrpg.services.armorservices.armorbodylocalization;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.repository.armor.ArmorBodyLocalizationRepository;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorBodyLocalizationServiceImpl implements ArmorBodyLocalizationService {

    private final ArmorBodyLocalizationRepository armorBodyLocalizationRepository;
    private final BodyLocalizationService bodyLocalizationService;

    @Autowired
    public ArmorBodyLocalizationServiceImpl(ArmorBodyLocalizationRepository armorBodyLocalizationRepository, BodyLocalizationService bodyLocalizationService) {
        this.armorBodyLocalizationRepository = armorBodyLocalizationRepository;
        this.bodyLocalizationService = bodyLocalizationService;
    }

    @Override
    public List<ArmorBodyLocalizationEntity> findByArmor(ArmorEntity armorEntity) {
        return armorBodyLocalizationRepository.findArmorBodyLocalizationEntitiesByArmor(armorEntity);
    }

    @Override
    public void save(ArmorBodyLocalizationEntity armorBodyLocalization) {
        armorBodyLocalizationRepository.save(armorBodyLocalization);
    }

    @Override
    public ArmorBodyLocalizationEntity mapToEntity(ArmorBodyLocalizationDto armorBodyLocalizationDto) {
        ArmorBodyLocalizationEntity armorBodyLocalizationEntity = new ArmorBodyLocalizationEntity();
        armorBodyLocalizationEntity.setId(armorBodyLocalizationDto.getId());
        armorBodyLocalizationEntity.setArmorPoints(armorBodyLocalizationDto.getArmorPoints());
        armorBodyLocalizationEntity.setBodyLocalization(
                bodyLocalizationService.mapToEntity(armorBodyLocalizationDto.getBodyLocalization())
        );
        return armorBodyLocalizationEntity;
    }
}
