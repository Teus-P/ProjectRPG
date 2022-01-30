package com.teus.projectrpg.entity.skill;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.skill.SkillType;
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