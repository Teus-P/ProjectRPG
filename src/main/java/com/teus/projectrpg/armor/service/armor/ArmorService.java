package com.teus.projectrpg.armor.service.armor;

import com.teus.projectrpg.armor.dto.ArmorDto;
import com.teus.projectrpg.armor.entity.ArmorEntity;

import java.util.List;

public interface ArmorService {

    List<ArmorDto> findAll();

    ArmorDto save(ArmorDto newArmor);

    void deleteById(Long id);

    ArmorEntity findByName(String name);
}
