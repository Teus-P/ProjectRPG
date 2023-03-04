package com.teus.projectrpg.availability.entity;


import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.availability.type.AvailabilityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "availability")
public class AvailabilityEntity extends BaseEntity<AvailabilityType> {

    @Column
    @Enumerated(EnumType.STRING)
    private AvailabilityType name;
}
