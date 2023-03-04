package com.teus.projectrpg.talent.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.talent.type.TalentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "talent")
public class TalentEntity extends BaseEntity<TalentType> {

    @Column
    @Enumerated(EnumType.STRING)
    private TalentType name;

    @Column
    private String maxLevel;
}