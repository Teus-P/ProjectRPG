package com.teus.projectrpg.service.armor.armorcategory;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import com.teus.projectrpg.repository.armor.ArmorCategoryRepository;
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
