package com.teus.projectrpg.entity;

import com.teus.projectrpg.type.ArmorQualityType;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Data
@Table(name = "armor_quality")
public class ArmorQualityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "armor_quality_type")
    private ArmorQualityType armorQualityType;
}