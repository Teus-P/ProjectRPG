package com.teus.projectrpg.condition.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.condition.type.ConditionType;
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

    @Column(name = "has_counter")
    private Boolean hasCounter;
}
