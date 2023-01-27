package com.teus.projectrpg.service.armorservices.armorcategory;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmorCategoryService {

    List<ArmorCategoryEntity> findAll();
}
