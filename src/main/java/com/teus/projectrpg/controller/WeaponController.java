package com.teus.projectrpg.controller;

import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.entity.weapon.*;
import com.teus.projectrpg.services.base.BaseService;
import com.teus.projectrpg.services.weaponservices.weapon.WeaponService;
import com.teus.projectrpg.services.weaponservices.weapongroup.WeaponGroupService;
import com.teus.projectrpg.services.weaponservices.weaponquality.WeaponQualityService;
import com.teus.projectrpg.services.weaponservices.weaponreach.WeaponReachService;
import com.teus.projectrpg.services.weaponservices.weapontype.WeaponTypeService;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import com.teus.projectrpg.type.weapon.WeaponReachType;
import com.teus.projectrpg.type.weapon.WeaponType;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WeaponController {

    private final WeaponService weaponService;
    private final WeaponTypeService weaponTypeService;
    private final WeaponGroupService weaponGroupService;
    private final WeaponReachService weaponReachService;
    private final WeaponQualityService weaponQualityService;
    private final BaseService baseService;

    public WeaponController(WeaponService weaponService, WeaponTypeService weaponTypeService, WeaponGroupService weaponGroupService, WeaponReachService weaponReachService, WeaponQualityService weaponQualityService, BaseService baseService) {
        this.weaponService = weaponService;
        this.weaponTypeService = weaponTypeService;
        this.weaponGroupService = weaponGroupService;
        this.weaponReachService = weaponReachService;
        this.weaponQualityService = weaponQualityService;
        this.baseService = baseService;
    }

//    @GetMapping("/weapon")
//    List<WeaponDto> getAllWeapons() {
//        List<WeaponDto> weaponDtos = new ArrayList<>();
//
//        for (WeaponEntity weaponEntity : weaponService.findAll()) {
//            weaponDtos.add(new WeaponDto(weaponEntity));
//        }
//
//        return weaponDtos;
//    }

    @GetMapping("/weapon")
    List<EntityModel<WeaponEntity>> getAllWeapons() {
        return weaponService.findAll().stream()
                .map(EntityModel::of)
                .toList();
    }

    @PutMapping("/weapon")
    WeaponEntity putWeapon(@RequestBody WeaponDto newWeapon) {
        WeaponEntity weaponEntity = weaponService.mapToEntity(newWeapon);
        WeaponEntity result = weaponService.findByName(weaponEntity.getName());
        if(result != null) {
            weaponEntity.setId(result.getId());
        }

        try {
            return weaponService.save(weaponEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @DeleteMapping("/weapon/{id}")
    void deleteWeapon(@PathVariable Long id) {
        try {
            weaponService.deleteById(id);
        } catch (Exception ex) {
            throw new ElementNotFoundException(id);
        }
    }

    @GetMapping("/weaponType")
    List<BaseDto<WeaponType, WeaponTypeEntity>> getAllWeaponTypes() {
        return this.baseService.getBaseDtosList(weaponTypeService.findAll());
    }

    @GetMapping("/weaponGroup")
    List<BaseDto<WeaponGroupType, WeaponGroupEntity>> getAllWeaponGroups() {
        return this.baseService.getBaseDtosList(weaponGroupService.findAll());
    }

    @GetMapping("/weaponReach")
    List<BaseDto<WeaponReachType, WeaponReachEntity>> getAllWeaponReaches() {
        return this.baseService.getBaseDtosList(weaponReachService.findAll());
    }

    @GetMapping("/weaponQuality")
    List<BaseDto<WeaponQualityType, WeaponQualityEntity>> getAllWeaponQualities() {
        return this.baseService.getBaseDtosList(weaponQualityService.findAll());
    }
}
