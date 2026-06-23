package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.availability.entity.AvailabilityEntity;
import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "armor")
public class ArmorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "name_translation", nullable = false, unique = true)
    private String nameTranslation;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "armor_category_id", nullable = false)
    private ArmorCategoryEntity armorCategory;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "armor_type_id", nullable = false)
    private ArmorTypeEntity armorType;

    @ManyToMany
    @JoinTable(name = "armor_body_localization",
            joinColumns = @JoinColumn(name = "armor_id"),
            inverseJoinColumns = @JoinColumn(name = "body_localization_id"))
    private List<BodyLocalizationEntity> bodyLocalizations = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "armor_armor_penalty",
            joinColumns = @JoinColumn(name = "armor_id"),
            inverseJoinColumns = @JoinColumn(name = "penalty_id"))
    private List<ArmorPenaltyEntity> armorPenalties = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "armor_armor_quality",
            joinColumns = @JoinColumn(name = "armor_id"),
            inverseJoinColumns = @JoinColumn(name = "quality_id"))
    private List<ArmorQualityEntity> armorQualities = new ArrayList<>();

    @Column(name = "price")
    private String price;

    @Column(name = "encumbrance")
    private String encumbrance;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "availability_id", nullable = false)
    private AvailabilityEntity availability;

    @Column(name = "is_base_armor", nullable = false)
    private Boolean isBaseArmor;

    @Column(name = "armor_points", nullable = false)
    private int armorPoints;

    @Column(name = "layer", nullable = false)
    private int layer;
}
