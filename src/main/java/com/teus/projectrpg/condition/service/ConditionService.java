package com.teus.projectrpg.condition.service;

import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.entity.ConditionEntity;
import com.teus.projectrpg.condition.type.ConditionType;

import java.util.List;

public interface ConditionService {

    List<ConditionDto> findAll();

    ConditionEntity findByName(ConditionType conditionType);
}
