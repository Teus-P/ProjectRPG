package com.teus.projectrpg.entity.talent;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.talent.TalentType;
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