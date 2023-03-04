package com.teus.projectrpg.injury.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.injury.type.InjuryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "injury")
public class InjuryEntity extends BaseEntity<InjuryType> {

    @Column
    @Enumerated(EnumType.STRING)
    private InjuryType name;
}
