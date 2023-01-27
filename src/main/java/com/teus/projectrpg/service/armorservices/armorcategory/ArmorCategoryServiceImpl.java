package com.teus.projectrpg.service.armorservices.armorcategory;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import com.teus.projectrpg.repository.armor.ArmorCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorCategoryServiceImpl implements ArmorCategoryService {

    private final ArmorCategoryRepository armorCategoryRepository;

    @Autowired
    public ArmorCategoryServiceImpl(ArmorCategoryRepository armorCategoryRepository) {
        this.armorCategoryRepository = armorCategoryRepository;
    }

    @Override
    public List<ArmorCategoryEntity> findAll() {
        return armorCategoryRepository.findAll();
    }
}
