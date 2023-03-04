package com.teus.projectrpg.condition.service;

import com.teus.projectrpg.condition.entity.ConditionEntity;
import com.teus.projectrpg.condition.repository.ConditionRepository;
import com.teus.projectrpg.condition.type.ConditionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionRepository conditionRepository;

    @Override
    public List<ConditionEntity> findAll() {
        return conditionRepository.findAll();
    }

    @Override
    public ConditionEntity findByName(ConditionType conditionType) {
        return conditionRepository.findConditionByName(conditionType);
    }
}
