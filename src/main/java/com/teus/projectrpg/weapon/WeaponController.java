package com.teus.projectrpg.weapon;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.dto.WeaponDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.weapon.mapper.WeaponMapper;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.weapon.mapper.WeaponContext;
import com.teus.projectrpg.weapon.service.weapon.WeaponService;
import com.teus.projectrpg.weapon.service.weapongroup.WeaponGroupService;
import com.teus.projectrpg.weapon.service.weaponquality.WeaponQualityService;
import com.teus.projectrpg.weapon.service.weaponreach.WeaponReachService;
import com.teus.projectrpg.weapon.service.weapontype.WeaponTypeService;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import com.teus.projectrpg.weapon.type.WeaponReachType;
import com.teus.projectrpg.weapon.type.WeaponType;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeaponController {

    private final WeaponService weaponService;
    private final WeaponTypeService weaponTypeService;
    private final WeaponGroupService weaponGroupService;
    private final WeaponReachService weaponReachService;
    private final WeaponQualityService weaponQualityService;
    private final BaseMapper baseMapper;
    private final WeaponMapper weaponMapper;

    @GetMapping("/weapon")
    List<WeaponDto> getAllWeapons() {
        return weaponMapper.toDtos(weaponService.findAll());
    }

    @PutMapping("/weapon")
    WeaponEntity putWeapon(@RequestBody WeaponDto newWeapon) {
        WeaponEntity weaponEntity = weaponMapper.toEntity(newWeapon, new WeaponContext());
        WeaponEntity result = weaponService.findByName(weaponEntity.getName());
        if (result != null) {
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
