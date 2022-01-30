package com.teus.projectrpg.entity.characteristic;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
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
