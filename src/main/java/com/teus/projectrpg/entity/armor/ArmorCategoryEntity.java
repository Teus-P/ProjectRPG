package com.teus.projectrpg.entity.armor;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "armor_category")
public class ArmorCategoryEntity extends BaseEntity<ArmorCategoryType> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ArmorCategoryType name;

//    @Override
//    public ArmorCategoryType getName() {
//        return name;
//    }
//
//    @Override
//    public void setName(ArmorCategoryType name) {
//        this.name = name;
//    }
}