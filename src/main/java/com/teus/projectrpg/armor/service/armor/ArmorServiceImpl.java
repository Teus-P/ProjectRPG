package com.teus.projectrpg.armor.service.armor;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import com.teus.projectrpg.armor.mapper.ArmorMapper;
import com.teus.projectrpg.armor.repository.ArmorRepository;
import com.teus.projectrpg.exception.ElementNotFoundException;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorServiceImpl implements ArmorService {

    private final ArmorRepository armorRepository;
    private final ArmorMapper armorMapper;

    @Override
    public List<ArmorDto> findAll() {
        return armorMapper.toDtos(armorRepository.findAll());
    }

    @Override
    @Transactional
    public ArmorDto saveDto(ArmorDto newArmor) {
        try {
            ArmorEntity armorEntity;
            if(newArmor.getId() != null) {
                armorEntity = findEntityById(newArmor.getId());
                armorMapper.updateEntityFromDto(newArmor, armorEntity);
            } else {
                armorEntity = armorMapper.toEntity(newArmor);
            }

            armorEntity.setIsBaseArmor(false);
            ArmorEntity savedArmor = armorRepository.save(armorEntity);
            return armorMapper.toDto(savedArmor);
        } catch (DataIntegrityViolationException e) {
            throw new FieldCannotBeNullException((PropertyValueException) e.getCause());
        }
    }

    @Override
    public void deleteById(Long id) {
        armorRepository.deleteById(id);
    }

    @Override
    public ArmorEntity findByName(String name) {
        return armorRepository.findArmorEntityByName(name);
    }

    @Override
    public ArmorEntity findEntityById(Long id) {
        return this.armorRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }
}
