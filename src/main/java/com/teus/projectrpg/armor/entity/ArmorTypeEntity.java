package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.armor.type.ArmorType;
import com.teus.projectrpg.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "armor_type")
public class ArmorTypeEntity extends BaseEntity<ArmorType> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ArmorType name;
}
