package com.teus.projectrpg.controller;

import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.entity.weapon.*;
import com.teus.projectrpg.mapper.WeaponMapper;
import com.teus.projectrpg.mapper.context.WeaponContext;
import com.teus.projectrpg.mapper.base.BaseMapper;
import com.teus.projectrpg.service.weaponservices.weapon.WeaponService;
import com.teus.projectrpg.service.weaponservices.weapongroup.WeaponGroupService;
import com.teus.projectrpg.service.weaponservices.weaponquality.WeaponQualityService;
import com.teus.projectrpg.service.weaponservices.weaponreach.WeaponReachService;
import com.teus.projectrpg.service.weaponservices.weapontype.WeaponTypeService;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import com.teus.projectrpg.type.weapon.WeaponReachType;
import com.teus.projectrpg.type.weapon.WeaponType;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WeaponController {

    private final WeaponService weaponService;
    private final WeaponTypeService weaponTypeService;
    private final WeaponGroupService weaponGroupService;
    private final WeaponReachService weaponReachService;
    private final WeaponQualityService weaponQualityService;
    private final BaseMapper baseMapper;
    private final WeaponMapper weaponMapper;

    public WeaponController(WeaponService weaponService, WeaponTypeService weaponTypeService, WeaponGroupService weaponGroupService, WeaponReachService weaponReachService, WeaponQualityService weaponQualityService, BaseMapper baseMapper, WeaponMapper weaponMapper) {
        this.weaponService = weaponService;
        this.weaponTypeService = weaponTypeService;
        this.weaponGroupService = weaponGroupService;
        this.weaponReachService = weaponReachService;
        this.weaponQualityService = weaponQualityService;
        this.baseMapper = baseMapper;
        this.weaponMapper = weaponMapper;
    }

    @GetMapping("/weapon")
    List<WeaponDto> getAllWeapons() {
        return weaponMapper.toDtos(weaponService.findAll());
    }

    @PutMapping("/weapon")
    WeaponEntity putWeapon(@RequestBody WeaponDto newWeapon) {
        WeaponEntity weaponEntity = weaponMapper.toEntity(newWeapon, new WeaponContext());
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
    List<BaseDto<WeaponType>> getAllWeaponTypes() {
        return this.baseMapper.toDtos(weaponTypeService.findAll());
    }

    @GetMapping("/weaponGroup")
    List<BaseDto<WeaponGroupType>> getAllWeaponGroups() {
        return this.baseMapper.toDtos(weaponGroupService.findAll());
    }

    @GetMapping("/weaponReach")
    List<BaseDto<WeaponReachType>> getAllWeaponReaches() {
        return this.baseMapper.toDtos(weaponReachService.findAll());
    }

    @GetMapping("/weaponQuality")
    List<BaseDto<WeaponQualityType>> getAllWeaponQualities() {
        return this.baseMapper.toDtos(weaponQualityService.findAll());
    }
}
