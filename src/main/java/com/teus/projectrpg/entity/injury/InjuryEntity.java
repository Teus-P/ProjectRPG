package com.teus.projectrpg.entity.injury;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.injury.InjuryType;
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