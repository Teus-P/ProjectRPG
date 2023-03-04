package com.teus.projectrpg.armor.service.armorcategory;

import com.teus.projectrpg.armor.entity.ArmorCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmorCategoryService {

    List<ArmorCategoryEntity> findAll();
}
