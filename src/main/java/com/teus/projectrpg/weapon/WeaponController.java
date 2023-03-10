package com.teus.projectrpg.weapon;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.dto.WeaponDto;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeaponController {

    private final WeaponService weaponService;
    private final WeaponTypeService weaponTypeService;
    private final WeaponGroupService weaponGroupService;
    private final WeaponReachService weaponReachService;
    private final WeaponQualityService weaponQualityService;

    @GetMapping("/weapon")
    public ResponseEntity<List<WeaponDto>> getAllWeapons() {
        List<WeaponDto> weaponDtos = weaponService.findAll();
        if (weaponDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(weaponDtos);
    }

    @PutMapping("/weapon")
    public ResponseEntity<WeaponDto> putWeapon(@Valid @RequestBody WeaponDto newWeapon) {
        return ResponseEntity.ok(weaponService.save(newWeapon));
    }

    @DeleteMapping("/weapon/{id}")
    public ResponseEntity<Void> deleteWeapon(@PathVariable Long id) {
        try {
            weaponService.deleteById(id);
            return ResponseEntity.notFound().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/weaponType")
    public ResponseEntity<List<BaseDto<WeaponType>>> getAllWeaponTypes() {
        List<BaseDto<WeaponType>> weaponTypeDtos = weaponTypeService.findAll();
        if (weaponTypeDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(weaponTypeDtos);
    }

    @GetMapping("/weaponGroup")
    public ResponseEntity<List<BaseDto<WeaponGroupType>>> getAllWeaponGroups() {
        List<BaseDto<WeaponGroupType>> weaponGroupDtos = weaponGroupService.findAll();
        if (weaponGroupDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(weaponGroupDtos);
    }

    @GetMapping("/weaponReach")
    public ResponseEntity<List<BaseDto<WeaponReachType>>> getAllWeaponReaches() {
        List<BaseDto<WeaponReachType>> weaponReachDtos = weaponReachService.findAll();
        if (weaponReachDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(weaponReachDtos);
    }

    @GetMapping("/weaponQuality")
    ResponseEntity<List<BaseDto<WeaponQualityType>>> getAllWeaponQualities() {
        List<BaseDto<WeaponQualityType>> weaponQualityDtos = weaponQualityService.findAll();
        if (weaponQualityDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(weaponQualityDtos);
    }
}
