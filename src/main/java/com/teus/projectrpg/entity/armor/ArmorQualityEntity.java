package com.teus.projectrpg.entity.armor;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Data
@Table(name = "armor_quality")
public class ArmorQualityEntity extends BaseEntity<ArmorQualityType> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ArmorQualityType name;
}
