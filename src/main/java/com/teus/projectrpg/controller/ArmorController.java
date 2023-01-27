package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.mapper.ArmorMapper;
import com.teus.projectrpg.mapper.base.BaseMapper;
import com.teus.projectrpg.mapper.context.ArmorContext;
import com.teus.projectrpg.service.armorservices.armor.ArmorService;
import com.teus.projectrpg.service.armorservices.armorcategory.ArmorCategoryService;
import com.teus.projectrpg.service.armorservices.armorpenalty.ArmorPenaltyService;
import com.teus.projectrpg.service.armorservices.armorquality.ArmorQualityService;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArmorController {

    private final BaseMapper baseMapper;
    private final ArmorService armorService;
    private final ArmorCategoryService armorCategoryService;
    private final ArmorPenaltyService armorPenaltyService;
    private final ArmorQualityService armorQualityService;
    private final ArmorMapper armorMapper;
    private final ArmorContext armorContext;

    public ArmorController(BaseMapper baseMapper, ArmorService armorService, ArmorCategoryService armorCategoryService, ArmorPenaltyService armorPenaltyService, ArmorQualityService armorQualityService, ArmorMapper armorMapper, ArmorContext armorContext) {
        this.baseMapper = baseMapper;
        this.armorService = armorService;
        this.armorCategoryService = armorCategoryService;
        this.armorPenaltyService = armorPenaltyService;
        this.armorQualityService = armorQualityService;
        this.armorMapper = armorMapper;
        this.armorContext = armorContext;
    }

    @GetMapping("/armor")
    List<ArmorDto> getAllArmors() {
        return armorMapper.toDtos(armorService.findAll());
    }

    @PutMapping("/armor")
    ArmorEntity putArmor(@RequestBody ArmorDto newArmor) {
        ArmorEntity armorEntity = armorMapper.toEntity(newArmor, armorContext);
        ArmorEntity result = armorService.findByName(armorEntity.getName());
        if (result != null) {
            armorEntity.setId(result.getId());
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

    @GetMapping("/armorCategory")
    List<BaseDto<ArmorCategoryType>> getAllArmorCategories() {
        return this.baseMapper.toDtos(armorCategoryService.findAll());
    }

    @GetMapping("/armorPenalty")
    List<BaseDto<ArmorPenaltyType>> getAllArmorPenalties() {
        return this.baseMapper.toDtos(armorPenaltyService.findAll());
    }

    @GetMapping("/armorQuality")
    List<BaseDto<ArmorQualityType>> getAllArmorQualities() {
        return this.baseMapper.toDtos(armorQualityService.findAll());
    }
}
