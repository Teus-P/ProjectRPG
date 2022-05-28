package com.teus.projectrpg.services.armorservices.armor;

import com.teus.projectrpg.dto.ArmorDto;
import com.teus.projectrpg.entity.armor.ArmorEntity;

import java.util.List;

public interface ArmorService {

    List<ArmorEntity> findAll();

    ArmorEntity save(ArmorEntity armorEntity);

    void deleteById(Long id);

    ArmorEntity findByName(String name);

    ArmorEntity mapToEntity(ArmorDto armorDto);

    ArmorDto mapToDto(ArmorEntity armorEntity);
}
