package com.teus.projectrpg.repository.condition;

import com.teus.projectrpg.entity.condition.ConditionEntity;
import com.teus.projectrpg.type.condition.ConditionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<ConditionEntity, Long> {

    ConditionEntity findConditionByName(ConditionType conditionType);
}
