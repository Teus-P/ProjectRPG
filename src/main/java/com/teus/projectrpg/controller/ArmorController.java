package com.teus.projectrpg.controller;

import com.teus.projectrpg.controller.exception.ElementNotFoundException;
import com.teus.projectrpg.controller.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.repository.armor.ArmorRepository;
import com.teus.projectrpg.services.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.services.armorquality.ArmorQualityService;
import com.teus.projectrpg.services.bodylocalization.BodyLocalizationService;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
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

        for (ArmorEntity armorEntity : armorRepository.findAll()) {
            armorDtos.add(new ArmorDto(armorEntity));
        }

        return armorDtos;
    }

    @PostMapping("/armor")
    ArmorEntity createNewArmor(@RequestBody ArmorDto newArmor) {
        ArmorEntity armorEntity = new ArmorEntity();
        armorEntity.setId(0L);
        armorEntity.setName(newArmor.getName());
        armorEntity.setNameTranslation(newArmor.getNameTranslation());
        armorEntity.setArmorCategory(newArmor.getArmorCategory());
        armorEntity.setArmorPoints(newArmor.getArmorPoints());

        ArrayList<BodyLocalizationEntity> bodyLocalizationEntities = new ArrayList<>();
        for (BodyLocalizationType bodyLocalization : newArmor.getBodyLocalization()) {
            bodyLocalizationEntities.add(bodyLocalizationService.findByType(bodyLocalization));
        }
        armorEntity.setBodyLocalizationEntities(bodyLocalizationEntities);

        ArrayList<ArmorPenaltyEntity> armorPenaltyEntities = new ArrayList<>();
        for (ArmorPenaltyType armorPenalty : newArmor.getPenalties()) {
            armorPenaltyEntities.add(armorPenaltyService.findByType(armorPenalty));
        }
        armorEntity.setArmorPenalties(armorPenaltyEntities);

        ArrayList<ArmorQualityEntity> armorQualityEntities = new ArrayList<>();
        for (ArmorQualityType armorQuality : newArmor.getQualities()) {
            armorQualityEntities.add(armorQualityService.findByType(armorQuality));
        }
        armorEntity.setArmorQualities(armorQualityEntities);

        try {
            return armorRepository.save(armorEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/armor/{id}")
    void deleteArmor(@PathVariable Long id) {
        try {
            armorRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }
}
