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

import java.util.ArrayList;
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

    @Autowired


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

        ArrayList<ArmorBodyLocalizationEntity> armorBodyLocalizationEntities = new ArrayList<>();
        for (ArmorBodyLocalizationDto armorBodyLocalizationDto : armorDto.getArmorBodyLocalizations()) {
            ArmorBodyLocalizationEntity armorBodyLocalizationEntity = armorBodyLocalizationService.mapToEntity(armorBodyLocalizationDto);
            armorBodyLocalizationEntity.setArmor(armorEntity);
            armorBodyLocalizationEntities.add(armorBodyLocalizationEntity);
        }
        armorEntity.setArmorBodyLocalizations(armorBodyLocalizationEntities);

        if (armorDto.getArmorPenalties() != null) {
            ArrayList<ArmorPenaltyEntity> armorPenaltyEntities = new ArrayList<>();
            for (BaseDto<ArmorPenaltyType, ArmorPenaltyEntity> armorPenalty : armorDto.getArmorPenalties()) {
                armorPenaltyEntities.add(armorPenaltyService.findByType(armorPenalty.getName()));
            }
            armorEntity.setArmorPenalties(armorPenaltyEntities);
        }

        if (armorDto.getArmorQualities() != null) {
            ArrayList<ArmorQualityEntity> armorQualityEntities = new ArrayList<>();
            for (BaseDto<ArmorQualityType, ArmorQualityEntity> armorQuality : armorDto.getArmorQualities()) {
                armorQualityEntities.add(armorQualityService.findByType(armorQuality.getName()));
            }
            armorEntity.setArmorQualities(armorQualityEntities);
        }

        return armorEntity;
    }

    @Override
    public ArmorDto mapToDto(ArmorEntity armorEntity) {
        ArmorDto armorDto = new ArmorDto();
        armorDto.setId(armorEntity.getId());
        armorDto.setName(armorEntity.getName());
        armorDto.setNameTranslation(armorEntity.getNameTranslation());
        armorDto.setArmorCategory(new BaseDto<>(armorEntity.getArmorCategory()));

        List<ArmorBodyLocalizationDto> armorBodyLocalizations = new ArrayList<>();
        for (ArmorBodyLocalizationEntity armorBodyLocalization : armorEntity.getArmorBodyLocalizations()) {
            armorBodyLocalizations.add(new ArmorBodyLocalizationDto(armorBodyLocalization));
        }
        armorDto.setArmorBodyLocalizations(armorBodyLocalizations);

        List<BaseDto<ArmorPenaltyType, ArmorPenaltyEntity>> armorPenalties = new ArrayList<>();
        for (ArmorPenaltyEntity armorPenalty : armorEntity.getArmorPenalties()) {
            armorPenalties.add(new BaseDto<>(armorPenalty));
        }
        armorDto.setArmorPenalties(armorPenalties);

        List<BaseDto<ArmorQualityType, ArmorQualityEntity>> armorQualities = new ArrayList<>();
        for (ArmorQualityEntity armorQuality : armorEntity.getArmorQualities()) {
            armorQualities.add(new BaseDto<>(armorQuality));
        }
        armorDto.setArmorQualities(armorQualities);

        return armorDto;
    }
}
