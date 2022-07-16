package com.teus.projectrpg.entity.condition;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.condition.ConditionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "condition_entity")
public class ConditionEntity extends BaseEntity<ConditionType> {

    @Column
    @Enumerated(EnumType.STRING)
    private ConditionType name;
}
