package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.armor.type.ArmorQualityType;
import com.teus.projectrpg.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "armor_quality")
public class ArmorQualityEntity extends BaseEntity<ArmorQualityType> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ArmorQualityType name;
}
