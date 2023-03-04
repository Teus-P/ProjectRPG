package com.teus.projectrpg.armor.service.armorcategory;

import com.teus.projectrpg.armor.entity.ArmorCategoryEntity;
import com.teus.projectrpg.armor.repository.ArmorCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorCategoryServiceImpl implements ArmorCategoryService {

    private final ArmorCategoryRepository armorCategoryRepository;

    @Override
    public List<ArmorCategoryEntity> findAll() {
        return armorCategoryRepository.findAll();
    }
}
