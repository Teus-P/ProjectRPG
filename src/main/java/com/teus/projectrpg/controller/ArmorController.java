package com.teus.projectrpg.controller;

import com.teus.projectrpg.controller.exception.ElementNotFoundException;
import com.teus.projectrpg.controller.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.services.armorservices.armor.ArmorService;
import com.teus.projectrpg.services.armorservices.armorcategory.ArmorCategoryService;
import com.teus.projectrpg.services.armorservices.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.services.armorservices.armorquality.ArmorQualityService;
import com.teus.projectrpg.services.base.BaseService;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArmorController {

    private final BaseService baseService;
    private final ArmorService armorService;
    private final ArmorCategoryService armorCategoryService;
    private final ArmorPenaltyService armorPenaltyService;
    private final ArmorQualityService armorQualityService;

    @Autowired
    public ArmorController(BaseService baseService, ArmorService armorService, ArmorCategoryService armorCategoryService, ArmorPenaltyService armorPenaltyService, ArmorQualityService armorQualityService) {
        this.baseService = baseService;
        this.armorService = armorService;
        this.armorCategoryService = armorCategoryService;
        this.armorPenaltyService = armorPenaltyService;
        this.armorQualityService = armorQualityService;
    }

    @GetMapping("/armor")
    List<ArmorDto> getAllArmors() {
        List<ArmorDto> armorDtos = new ArrayList<>();

        for (ArmorEntity armorEntity : armorService.findAll()) {
            armorDtos.add(armorService.mapToDto(armorEntity));
        }

        return armorDtos;
    }

    @PutMapping("/armor")
    ArmorEntity putArmor(@RequestBody ArmorDto newArmor) {
        ArmorEntity armorEntity = armorService.mapToEntity(newArmor);

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

    @GetMapping("/armorCategory")
    List<BaseDto<ArmorCategoryType, ArmorCategoryEntity>> getAllArmorCategories() {
        List<BaseDto<ArmorCategoryType, ArmorCategoryEntity>> armorCategoriesDtos = new ArrayList<>();

        for(ArmorCategoryEntity armorCategory : armorCategoryService.findAll()) {
            armorCategoriesDtos.add(baseService.mapToDto(armorCategory));
        }

        return armorCategoriesDtos;
    }

    @GetMapping("/armorPenalty")
    List<BaseDto<ArmorPenaltyType, ArmorPenaltyEntity>> getAllArmorPenalties() {
        List<BaseDto<ArmorPenaltyType, ArmorPenaltyEntity>> armorPenaltiesDtos = new ArrayList<>();

        for(ArmorPenaltyEntity armorPenalty : armorPenaltyService.findAll()) {
            armorPenaltiesDtos.add(baseService.mapToDto(armorPenalty));
        }

        return armorPenaltiesDtos;
    }

    @GetMapping("/armorQuality")
    List<BaseDto<ArmorQualityType, ArmorQualityEntity>> getAllArmorQualities() {
        List<BaseDto<ArmorQualityType, ArmorQualityEntity>> armorQualitiesDtos = new ArrayList<>();

        for(ArmorQualityEntity armorQuality : armorQualityService.findAll()) {
            armorQualitiesDtos.add(baseService.mapToDto(armorQuality));
        }

        return armorQualitiesDtos;
    }
}
