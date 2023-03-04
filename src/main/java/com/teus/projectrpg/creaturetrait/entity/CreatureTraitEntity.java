package com.teus.projectrpg.creaturetrait.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.creaturetrait.type.CreatureTraitType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "creature_trait")
public class CreatureTraitEntity extends BaseEntity<CreatureTraitType> {

    @Column
    @Enumerated(EnumType.STRING)
    private CreatureTraitType name;

    @Column
    private Boolean hasValue;

}
