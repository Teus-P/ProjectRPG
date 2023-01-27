package com.teus.projectrpg.service.condition;

import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.repository.condition.ConditionRepository;
import com.teus.projectrpg.type.condition.ConditionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionServiceImpl implements ConditionService {

    private final ConditionRepository conditionRepository;

    @Autowired
    public ConditionServiceImpl(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public List<ConditionEntity> findAll() {
        return conditionRepository.findAll();
    }

    @Override
    public ConditionEntity findByName(ConditionType conditionType) {
        return conditionRepository.findConditionByName(conditionType);
    }
}
