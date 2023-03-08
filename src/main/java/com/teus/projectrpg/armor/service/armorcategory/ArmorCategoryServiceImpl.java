package com.teus.projectrpg.armor.service.armorcategory;

import com.teus.projectrpg.armor.repository.ArmorCategoryRepository;
import com.teus.projectrpg.armor.type.ArmorCategoryType;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorCategoryServiceImpl implements ArmorCategoryService {

    private final ArmorCategoryRepository armorCategoryRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<ArmorCategoryType>> findAll() {
        return this.baseMapper.toDtos(armorCategoryRepository.findAll());
    }
}
