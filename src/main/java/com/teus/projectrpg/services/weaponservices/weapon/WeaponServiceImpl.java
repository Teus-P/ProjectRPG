package com.teus.projectrpg.services.weaponservices.weapon;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.dto.WeaponDto;
import com.teus.projectrpg.dto.WeaponQualityValueDto;
import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.entity.weapon.WeaponQualityValueEntity;
import com.teus.projectrpg.repository.weapon.WeaponRepository;
import com.teus.projectrpg.services.weaponservices.weapongroup.WeaponGroupService;
import com.teus.projectrpg.services.weaponservices.weaponquality.WeaponQualityService;
import com.teus.projectrpg.services.weaponservices.weaponreach.WeaponReachService;
import com.teus.projectrpg.services.weaponservices.weapontype.WeaponTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;
    private final WeaponTypeService weaponTypeService;
    private final WeaponGroupService weaponGroupService;
    private final WeaponReachService weaponReachService;
    private final WeaponQualityService weaponQualityService;

    @Autowired
    public WeaponServiceImpl(WeaponRepository weaponRepository, WeaponTypeService weaponTypeService, WeaponGroupService weaponGroupService, WeaponReachService weaponReachService, WeaponQualityService weaponQualityService) {
        this.weaponRepository = weaponRepository;
        this.weaponTypeService = weaponTypeService;
        this.weaponGroupService = weaponGroupService;
        this.weaponReachService = weaponReachService;
        this.weaponQualityService = weaponQualityService;
    }

    @Override
    public List<WeaponEntity> findAll() {
        return weaponRepository.findAll();
    }

    @Override
    public WeaponEntity save(WeaponEntity weaponEntity) {
        return weaponRepository.save(weaponEntity);
    }

    @Override
    public WeaponEntity findByName(String name) {
        return weaponRepository.findWeaponEntityByName(name);
    }

    @Override
    public void deleteById(Long id) {
        weaponRepository.deleteById(id);
    }

    @Override
    public WeaponEntity mapToEntity(WeaponDto weaponDto) {
        WeaponEntity weaponEntity = new WeaponEntity();

        if (weaponDto.getId() != null) {
            weaponEntity.setId(weaponDto.getId());
        } else {
            weaponEntity.setId(0L);
        }

        weaponEntity.setName(weaponDto.getName());
        weaponEntity.setNameTranslation(weaponDto.getNameTranslation());
        weaponEntity.setWeaponType(weaponTypeService.findByName(weaponDto.getWeaponType().getName()));
        weaponEntity.setWeaponGroup(weaponGroupService.findByName(weaponDto.getWeaponGroupType().getName()));
        weaponEntity.setWeaponReach(weaponReachService.findByName(weaponDto.getWeaponReach().getName()));
        weaponEntity.setWeaponRange(weaponDto.getWeaponRange());
        weaponEntity.setIsUsingStrength(weaponDto.getIsUsingStrength());
        weaponEntity.setIsUsingStrengthInRange(weaponDto.getIsUsingStrengthInRange());
        weaponEntity.setDamage(weaponDto.getDamage());

        if (weaponDto.getWeaponQualities() != null) {
            ArrayList<WeaponQualityValueEntity> weaponQualityValueEntities = new ArrayList<>();
            for (WeaponQualityValueDto weaponQuality : weaponDto.getWeaponQualities()) {
                WeaponQualityValueEntity newWeaponQualityValue = new WeaponQualityValueEntity();
                newWeaponQualityValue.setWeapon(weaponEntity);
                newWeaponQualityValue.setWeaponQuality(weaponQualityService.findByType(weaponQuality.getName()));
                newWeaponQualityValue.setValue(weaponQuality.getValue());
                weaponQualityValueEntities.add(newWeaponQualityValue);
            }
            weaponEntity.setWeaponQualities(weaponQualityValueEntities);
        }

        return weaponEntity;
    }

    @Override
    public WeaponDto mapToDto(WeaponEntity weaponEntity) {
        WeaponDto weaponDto = new WeaponDto();
        weaponDto.setId(weaponEntity.getId());
        weaponDto.setName(weaponEntity.getName());
        weaponDto.setNameTranslation(weaponEntity.getNameTranslation());
        weaponDto.setWeaponType(new BaseDto<>(weaponEntity.getWeaponType()));
        weaponDto.setWeaponGroupType(new BaseDto<>(weaponEntity.getWeaponGroup()));
        weaponDto.setWeaponReach(new BaseDto<>(weaponEntity.getWeaponReach()));
        weaponDto.setWeaponRange(weaponEntity.getWeaponRange());
        weaponDto.setIsUsingStrength(weaponEntity.getIsUsingStrength());
        weaponDto.setIsUsingStrengthInRange(weaponEntity.getIsUsingStrengthInRange());
        weaponDto.setDamage(weaponEntity.getDamage());

        ArrayList<WeaponQualityValueDto> weaponQualities = new ArrayList<>();
        for (WeaponQualityValueEntity weaponQualityValueEntity : weaponEntity.getWeaponQualities()) {
            weaponQualities.add(new WeaponQualityValueDto(weaponQualityValueEntity));
        }
        weaponDto.setWeaponQualities(weaponQualities);

        return weaponDto;
    }
}
