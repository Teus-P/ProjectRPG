package com.teus.projectrpg.weapon.service.weapon;

import com.teus.projectrpg.exception.FieldCannotBeNullException;
import com.teus.projectrpg.weapon.dto.WeaponDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.mapper.WeaponContext;
import com.teus.projectrpg.weapon.mapper.WeaponMapper;
import com.teus.projectrpg.weapon.repository.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;
    private final WeaponMapper weaponMapper;

    @Override
    public List<WeaponDto> findAll() {
        return weaponMapper.toDtos(weaponRepository.findAll());
    }

    @Override
    public WeaponDto save(WeaponDto newWeapon) {
        WeaponEntity weaponEntity = weaponMapper.toEntity(newWeapon, new WeaponContext());
        try {
            WeaponEntity savedWeaponEntity = weaponRepository.save(weaponEntity);
            return weaponMapper.toDto(savedWeaponEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new FieldCannotBeNullException((PropertyValueException) ex.getCause());
        }
    }

    @Override
    public WeaponEntity findByName(String name) {
        return weaponRepository.findWeaponEntityByName(name);
    }

    @Override
    public void deleteById(Long id) {
        weaponRepository.deleteById(id);
    }
}
