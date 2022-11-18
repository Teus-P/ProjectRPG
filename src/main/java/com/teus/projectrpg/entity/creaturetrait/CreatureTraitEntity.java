package com.teus.projectrpg.entity.creaturetrait;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.creaturetrait.CreatureTraitType;
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
