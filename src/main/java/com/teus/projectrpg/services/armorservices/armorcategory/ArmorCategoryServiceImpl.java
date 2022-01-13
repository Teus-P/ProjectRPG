package com.teus.projectrpg.services.armorservices.armorcategory;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import com.teus.projectrpg.repository.armor.ArmorCategoryRepository;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
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

    @Override
    public ArmorCategoryEntity findByName(ArmorCategoryType armorCategoryType) {
        return armorCategoryRepository.findArmorCategoryEntityByName(armorCategoryType);
    }
}
