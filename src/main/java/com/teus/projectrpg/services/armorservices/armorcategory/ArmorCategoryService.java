package com.teus.projectrpg.services.armorservices.armorcategory;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmorCategoryService {

    List<ArmorCategoryEntity> findAll();
    ArmorCategoryEntity findByName(ArmorCategoryType armorCategoryType);
}
