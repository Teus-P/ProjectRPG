package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.armor.type.ArmorCategoryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "armor_category")
public class ArmorCategoryEntity extends BaseEntity<ArmorCategoryType> {

    @Column
    @Enumerated(EnumType.STRING)
    private ArmorCategoryType name;
}