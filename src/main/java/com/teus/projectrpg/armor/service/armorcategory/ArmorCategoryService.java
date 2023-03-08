package com.teus.projectrpg.armor.service.armorcategory;

import com.teus.projectrpg.armor.type.ArmorCategoryType;
import com.teus.projectrpg.base.dto.BaseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmorCategoryService {

    List<BaseDto<ArmorCategoryType>> findAll();
}
