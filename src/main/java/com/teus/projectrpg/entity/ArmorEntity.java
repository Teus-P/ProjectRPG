package com.teus.projectrpg.entity;

import com.teus.projectrpg.type.ArmorCategory;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "armor_category")
    private ArmorCategory armorCategory;

    @ManyToMany
    @JoinTable(name = "armor_body_localizations",
            joinColumns = @JoinColumn(name = "armor_id"),
            inverseJoinColumns = @JoinColumn(name = "body_localizations_id"))
    private List<BodyLocalizationEntity> bodyLocalizationEntities = new ArrayList<>();

    @Column(name = "armor_points", nullable = false)
    private Integer armorPoints;

    @ManyToMany
    @JoinTable(name = "armor_armor_penalty",
            joinColumns = @JoinColumn(name = "armor_id"),
            inverseJoinColumns = @JoinColumn(name = "penalty_id"))
    private List<ArmorPenaltyEntity> armorPenalties = new ArrayList<>();

//    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    @Column(name = "penalty")
//    private List<ArmorPenalty> penalty;
//
//    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    @Column(name = "armor_qualities")
//    private List<ArmorQualities> armorQualities;
}
