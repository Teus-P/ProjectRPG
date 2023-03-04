package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
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
