package com.teus.projectrpg.armor.service.armor;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import com.teus.projectrpg.armor.mapper.ArmorContext;
import com.teus.projectrpg.armor.mapper.ArmorMapper;
import com.teus.projectrpg.armor.repository.ArmorRepository;
import com.teus.projectrpg.exception.FieldCannotBeNullException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorServiceImpl implements ArmorService {

    private final ArmorRepository armorRepository;
    private final ArmorMapper armorMapper;
    private final ArmorContext armorContext;

    @Override
    public List<ArmorDto> findAll() {
        return armorMapper.toDtos(armorRepository.findAll());
    }

    @Override
    public ArmorDto save(ArmorDto newArmor) {
        ArmorEntity armorEntity = armorMapper.toEntity(newArmor, armorContext);
        ArmorEntity result = this.findByName(armorEntity.getName());
        if (result != null) {
            armorEntity.setId(result.getId());
        }

        try {
            ArmorEntity saveArmorEntity = armorRepository.save(armorEntity);
            return armorMapper.toDto(saveArmorEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new FieldCannotBeNullException((PropertyValueException) ex.getCause());
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
}
