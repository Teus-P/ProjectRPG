package com.teus.projectrpg.services.condition;

import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.type.condition.ConditionType;

import java.util.List;

public interface ConditionService {

    List<ConditionEntity> findAll();

    ConditionEntity findByName(ConditionType conditionType);
}
