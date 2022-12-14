package com.teus.projectrpg.services.armorservices.armor;

import com.teus.projectrpg.dto.ArmorBodyLocalizationDto;
import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.repository.armor.ArmorRepository;
import com.teus.projectrpg.services.armorservices.armorbodylocalization.ArmorBodyLocalizationService;
import com.teus.projectrpg.services.armorservices.armorcategory.ArmorCategoryService;
import com.teus.projectrpg.services.armorservices.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.services.armorservices.armorquality.ArmorQualityService;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorServiceImpl implements ArmorService {

    private final ArmorRepository armorRepository;
    private final ArmorCategoryService armorCategoryService;
    private final ArmorPenaltyService armorPenaltyService;
    private final ArmorQualityService armorQualityService;
    private final ArmorBodyLocalizationService armorBodyLocalizationService;

    @Autowired
    public ArmorServiceImpl(ArmorRepository armorRepository, ArmorCategoryService armorCategoryService, ArmorPenaltyService armorPenaltyService, ArmorQualityService armorQualityService, ArmorBodyLocalizationService armorBodyLocalizationService) {
        this.armorRepository = armorRepository;
        this.armorCategoryService = armorCategoryService;
        this.armorPenaltyService = armorPenaltyService;
        this.armorQualityService = armorQualityService;
        this.armorBodyLocalizationService = armorBodyLocalizationService;
    }

    @Override
    public List<ArmorEntity> findAll() {
        return armorRepository.findAll();
    }

    @Override
    public ArmorEntity save(ArmorEntity armorEntity) {
        return armorRepository.save(armorEntity);
    }

    @Override
    public void deleteById(Long id) {
        armorRepository.deleteById(id);
    }

    @Override
    public ArmorEntity findByName(String name) {
        return armorRepository.findArmorEntityByName(name);
    }

    @Override
    public ArmorEntity mapToEntity(ArmorDto armorDto) {
        ArmorEntity armorEntity = new ArmorEntity();

        if (armorDto.getId() != null) {
            armorEntity.setId(armorDto.getId());
        } else {
            armorEntity.setId(0L);
        }

        armorEntity.setName(armorDto.getName());
        armorEntity.setNameTranslation(armorDto.getNameTranslation());
        armorEntity.setArmorCategory(armorCategoryService.findByName(armorDto.getArmorCategory().getName()));

        setArmorBodyLocalizations(armorDto, armorEntity);
        setArmorPenalties(armorDto, armorEntity);
        setArmorQualities(armorDto, armorEntity);

        return armorEntity;
    }

    private void setArmorBodyLocalizations(ArmorDto armorDto, ArmorEntity armorEntity) {
        for (ArmorBodyLocalizationDto armorBodyLocalizationDto : armorDto.getArmorBodyLocalizations()) {
            ArmorBodyLocalizationEntity armorBodyLocalizationEntity = armorBodyLocalizationService.mapToEntity(armorBodyLocalizationDto);
            armorBodyLocalizationEntity.setArmor(armorEntity);
            armorEntity.addBodyLocalization(armorBodyLocalizationEntity);
        }
    }

    private void setArmorPenalties(ArmorDto armorDto, ArmorEntity armorEntity) {
        if (armorDto.getArmorPenalties() != null) {
            for (BaseDto<ArmorPenaltyType, ArmorPenaltyEntity> armorPenalty : armorDto.getArmorPenalties()) {
                armorEntity.addArmorPenalty(armorPenaltyService.findByType(armorPenalty.getName()));
            }
        }
    }

    private void setArmorQualities(ArmorDto armorDto, ArmorEntity armorEntity) {
        if (armorDto.getArmorQualities() != null) {
            for (BaseDto<ArmorQualityType, ArmorQualityEntity> armorQuality : armorDto.getArmorQualities()) {
                armorEntity.addArmorQuality(armorQualityService.findByType(armorQuality.getName()));
            }
        }
    }
}
