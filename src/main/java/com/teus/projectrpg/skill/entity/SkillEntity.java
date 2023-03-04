package com.teus.projectrpg.skill.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class SkillEntity extends BaseEntity<SkillType> {

    @Column
    @Enumerated(EnumType.STRING)
    private SkillType name;
}