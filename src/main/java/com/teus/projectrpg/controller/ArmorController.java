package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.ArmorEntity;
import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.ArmorQualityEntity;
import com.teus.projectrpg.entity.BodyLocalizationEntity;
import com.teus.projectrpg.repository.ArmorRepository;
import com.teus.projectrpg.services.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.services.armorquality.ArmorQualityService;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import com.teus.projectrpg.type.ArmorPenaltyType;
import com.teus.projectrpg.type.ArmorQualityType;
import com.teus.projectrpg.type.BodyLocalizationType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArmorController {

    private final ArmorRepository armorRepository;
    private final BodyLocalizationService bodyLocalizationService;
    private final ArmorPenaltyService armorPenaltyService;
    private final ArmorQualityService armorQualityService;

    public ArmorController(ArmorRepository armorRepository, BodyLocalizationService bodyLocalizationService, ArmorPenaltyService armorPenaltyService, ArmorQualityService armorQualityService) {
        this.armorRepository = armorRepository;
        this.bodyLocalizationService = bodyLocalizationService;
        this.armorPenaltyService = armorPenaltyService;
        this.armorQualityService = armorQualityService;
    }

    @GetMapping("/armor")
    List<ArmorDto> getAll() {
        List<ArmorDto> armorDtos = new ArrayList<>();

        for(ArmorEntity armorEntity: armorRepository.findAll()) {
            armorDtos.add(new ArmorDto(armorEntity));
        }

        return armorDtos;
    }

    @PostMapping("/armor")
    ArmorEntity createNewArmor(@RequestBody ArmorDto newArmor) {
        ArmorEntity armorEntity = new ArmorEntity();
        armorEntity.setId(0L);
        armorEntity.setName(newArmor.getName());
        armorEntity.setArmorCategory(newArmor.getArmorCategory());
        armorEntity.setArmorPoints(newArmor.getArmorPoints());

        ArrayList<BodyLocalizationEntity> bodyLocalizationEntities = new ArrayList<>();
        for(BodyLocalizationType bodyLocalization: newArmor.getBodyLocalization()) {
            bodyLocalizationEntities.add(bodyLocalizationService.findByType(bodyLocalization));
        }
        armorEntity.setBodyLocalizationEntities(bodyLocalizationEntities);

        ArrayList<ArmorPenaltyEntity> armorPenaltyEntities = new ArrayList<>();
        for(ArmorPenaltyType armorPenalty: newArmor.getPenalties()) {
            armorPenaltyEntities.add(armorPenaltyService.findByType(armorPenalty));
        }
        armorEntity.setArmorPenalties(armorPenaltyEntities);

        ArrayList<ArmorQualityEntity> armorQualityEntities = new ArrayList<>();
        for(ArmorQualityType armorQuality: newArmor.getQualities()) {
            armorQualityEntities.add(armorQualityService.findByType(armorQuality));
        }
        armorEntity.setArmorQualities(armorQualityEntities);

        return armorRepository.save(armorEntity);
    }

    @DeleteMapping("/armor/{id}")
    void deleteArmor(@PathVariable Long id) {
        armorRepository.deleteById(id);
    }
}
