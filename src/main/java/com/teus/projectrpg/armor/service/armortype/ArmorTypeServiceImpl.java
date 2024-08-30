package com.teus.projectrpg.armor.service.armortype;

import com.teus.projectrpg.armor.repository.ArmorTypeRepository;
import com.teus.projectrpg.armor.type.ArmorType;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorTypeServiceImpl implements ArmorTypeService {

    private final ArmorTypeRepository armorTypeRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<ArmorType>> findAll() {
        return this.baseMapper.toDtos(armorTypeRepository.findAll());
    }
}
