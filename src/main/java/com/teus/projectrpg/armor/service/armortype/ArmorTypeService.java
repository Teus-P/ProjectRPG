package com.teus.projectrpg.armor.service.armortype;

import com.teus.projectrpg.armor.type.ArmorType;
import com.teus.projectrpg.base.dto.BaseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmorTypeService {

    List<BaseDto<ArmorType>> findAll();
}
