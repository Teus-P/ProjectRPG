package com.teus.projectrpg.characteristic.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "characteristic")
public class CharacteristicEntity extends BaseEntity<CharacteristicType> {

    @Column
    @Enumerated(EnumType.STRING)
    private CharacteristicType name;
}
