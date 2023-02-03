package com.teus.projectrpg.entity.shared;


import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.AvailabilityType;
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
