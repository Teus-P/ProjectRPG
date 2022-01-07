package com.teus.projectrpg.controller;

import com.teus.projectrpg.controller.exception.FieldCannotBeNullException;
import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.dto.WeaponQualityValueDto;
import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.entity.weapon.WeaponQualityValueEntity;
import com.teus.projectrpg.services.weapon.WeaponService;
import com.teus.projectrpg.services.weaponquality.WeaponQualityService;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WeaponController {

    private final WeaponService weaponService;
    private final WeaponQualityService weaponQualityService;

    public WeaponController(WeaponService weaponService, WeaponQualityService weaponQualityService) {
        this.weaponService = weaponService;
        this.weaponQualityService = weaponQualityService;
    }

    @GetMapping("/weapon")
    List<WeaponDto> getAllWeapons() {
        List<WeaponDto> weaponDtos = new ArrayList<>();

        for (WeaponEntity weaponEntity : weaponService.findAll()) {
            weaponDtos.add(new WeaponDto(weaponEntity));
        }

        return weaponDtos;
    }

    @PostMapping("/weapon")
    WeaponEntity createNewWeapon(@RequestBody WeaponDto newWeapon) {
        WeaponEntity weaponEntity = new WeaponEntity();
        weaponEntity.setId(0L);
        weaponEntity.setName(newWeapon.getName());
        weaponEntity.setNameTranslation(newWeapon.getNameTranslation());
        weaponEntity.setWeaponType(newWeapon.getWeaponType());
        weaponEntity.setWeaponGroup(newWeapon.getWeaponGroupType());
        weaponEntity.setWeaponRange(newWeapon.getWeaponRange());
        weaponEntity.setIsUsingStrength(newWeapon.getIsUsingStrength());
        weaponEntity.setDamage(newWeapon.getDamage());

        if (newWeapon.getWeaponQualities() != null) {
            ArrayList<WeaponQualityValueEntity> weaponQualityValueEntities = new ArrayList<>();
            for (WeaponQualityValueDto weaponQuality : newWeapon.getWeaponQualities()) {
                WeaponQualityValueEntity newWeaponQualityValue = new WeaponQualityValueEntity();
                newWeaponQualityValue.setWeaponEntity(weaponEntity);
                newWeaponQualityValue.setWeaponQualityEntity(weaponQualityService.findByType(weaponQuality.getWeaponQualityType()));
                newWeaponQualityValue.setValue(weaponQuality.getValue());
                weaponQualityValueEntities.add(newWeaponQualityValue);
            }
            weaponEntity.setWeaponQualities(weaponQualityValueEntities);
        }

        try {
            return weaponService.save(weaponEntity);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

}
