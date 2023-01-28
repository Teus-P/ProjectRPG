package com.teus.projectrpg.service.condition;

import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.repository.condition.ConditionRepository;
import com.teus.projectrpg.type.condition.ConditionType;
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
