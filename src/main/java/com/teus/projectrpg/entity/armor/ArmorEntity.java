package com.teus.projectrpg.entity.armor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
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

    public void addBodyLocalization(ArmorBodyLocalizationEntity armorBodyLocalization) {
        this.armorBodyLocalizations.add(armorBodyLocalization);
    }

    public void addArmorPenalty(ArmorPenaltyEntity armorPenalty) {
        this.armorPenalties.add(armorPenalty);
    }

    public void addArmorQuality(ArmorQualityEntity armorQuality) {
        this.armorQualities.add(armorQuality);
    }
}
