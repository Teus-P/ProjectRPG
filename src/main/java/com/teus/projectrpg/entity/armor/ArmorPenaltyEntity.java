package com.teus.projectrpg.entity.armor;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "armor_penalty")
public class ArmorPenaltyEntity extends BaseEntity<ArmorPenaltyType> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ArmorPenaltyType name;
}
