package com.teus.projectrpg.armor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.availability.entity.AvailabilityEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @JsonIgnore
    @OneToMany(
            mappedBy = "armor",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<ArmorBodyLocalizationEntity> armorBodyLocalizations = new ArrayList<>();

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
}
