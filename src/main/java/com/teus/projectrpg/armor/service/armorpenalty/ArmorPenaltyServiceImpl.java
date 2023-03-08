package com.teus.projectrpg.armor.service.armorpenalty;

import com.teus.projectrpg.armor.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.armor.repository.ArmorPenaltyRepository;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorPenaltyServiceImpl implements ArmorPenaltyService {

    private final ArmorPenaltyRepository armorPenaltyRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<ArmorPenaltyType>> findAll() {
        return this.baseMapper.toDtos(armorPenaltyRepository.findAll());
    }

    @Override
    public void save(ArmorPenaltyEntity armorPenalty) {
        armorPenaltyRepository.save(armorPenalty);
    }
}
