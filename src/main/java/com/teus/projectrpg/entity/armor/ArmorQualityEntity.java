package com.teus.projectrpg.entity.armor;

import com.teus.projectrpg.type.armor.ArmorQualityType;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Data
@Table(name = "armor_quality")
public class ArmorQualityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "armor_quality_type")
    private ArmorQualityType armorQualityType;
}
