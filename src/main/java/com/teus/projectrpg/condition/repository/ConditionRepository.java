package com.teus.projectrpg.condition.repository;

import com.teus.projectrpg.condition.entity.ConditionEntity;
import com.teus.projectrpg.condition.type.ConditionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<ConditionEntity, Long> {
    ConditionEntity findConditionByName(ConditionType conditionType);
}
