package com.teus.projectrpg.controller;

import com.teus.projectrpg.controller.exception.ElementNotFoundException;
import com.teus.projectrpg.controller.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.*;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.services.armorservices.armor.ArmorService;
import com.teus.projectrpg.services.armorservices.armorcategory.ArmorCategoryService;
import com.teus.projectrpg.services.armorservices.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.services.armorservices.armorquality.ArmorQualityService;
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

    private final ArmorService armorService;
    private final BodyLocalizationService bodyLocalizationService;
    private final ArmorPenaltyService armorPenaltyService;
    private final ArmorQualityService armorQualityService;
    private final ArmorCategoryService armorCategoryService;

    public ArmorController(ArmorService armorService, BodyLocalizationService bodyLocalizationService, ArmorPenaltyService armorPenaltyService, ArmorQualityService armorQualityService, ArmorCategoryService armorCategoryService) {
        this.armorService = armorService;
        this.bodyLocalizationService = bodyLocalizationService;
        this.armorPenaltyService = armorPenaltyService;
        this.armorQualityService = armorQualityService;
        this.armorCategoryService = armorCategoryService;
    }

    @GetMapping("/armor")
    List<ArmorDto> getAllArmors() {
        List<ArmorDto> armorDtos = new ArrayList<>();

        for (ArmorEntity armorEntity : armorService.findAll()) {
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
        armorEntity.setArmorCategory(armorCategoryService.findByName(newArmor.getArmorCategory().getName()));
        armorEntity.setArmorPoints(newArmor.getArmorPoints());

        ArrayList<BodyLocalizationEntity> bodyLocalizationEntities = new ArrayList<>();
        for (BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalization : newArmor.getBodyLocalization()) {
            bodyLocalizationEntities.add(bodyLocalizationService.findByName(bodyLocalization.getName()));
        }
        armorEntity.setBodyLocalizations(bodyLocalizationEntities);

        if (newArmor.getPenalties() != null) {
            ArrayList<ArmorPenaltyEntity> armorPenaltyEntities = new ArrayList<>();
            for (BaseDto<ArmorPenaltyType, ArmorPenaltyEntity> armorPenalty : newArmor.getPenalties()) {
                armorPenaltyEntities.add(armorPenaltyService.findByType(armorPenalty.getName()));
            }
            armorEntity.setArmorPenalties(armorPenaltyEntities);
        }

        if (newArmor.getQualities() != null) {
            ArrayList<ArmorQualityEntity> armorQualityEntities = new ArrayList<>();
            for (BaseDto<ArmorQualityType, ArmorQualityEntity> armorQuality : newArmor.getQualities()) {
                armorQualityEntities.add(armorQualityService.findByType(armorQuality.getName()));
            }
            armorEntity.setArmorQualities(armorQualityEntities);
        }

        try {
            return armorService.save(armorEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/armor/{id}")
    void deleteArmor(@PathVariable Long id) {
        try {
            armorService.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }
}
