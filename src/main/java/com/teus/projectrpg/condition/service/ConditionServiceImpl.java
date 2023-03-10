package com.teus.projectrpg.condition.service;

import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.entity.ConditionEntity;
import com.teus.projectrpg.condition.mapper.ConditionMapper;
import com.teus.projectrpg.condition.repository.ConditionRepository;
import com.teus.projectrpg.condition.type.ConditionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionRepository conditionRepository;
    private final ConditionMapper conditionMapper;

    @Override
    public List<ConditionDto> findAll() {
        return conditionMapper.toDtos(conditionRepository.findAll());
    }

    @Override
    public ConditionEntity findByName(ConditionType conditionType) {
        return conditionRepository.findConditionByName(conditionType);
    }
}
