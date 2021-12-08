package com.teus.projectrpg.entity;

import com.teus.projectrpg.type.ArmorCategory;
import com.teus.projectrpg.type.ArmorPenalty;
import com.teus.projectrpg.type.ArmorQualities;
import com.teus.projectrpg.type.BodyLocalization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "armor")
public class Armor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "armor_category")
    private ArmorCategory armorCategory;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(name = "body_localization")
    private List<BodyLocalization> bodyLocalization;

    @Column(name = "armor_points", nullable = false)
    private Integer armorPoints;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(name = "penalty")
    private List<ArmorPenalty> penalty;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @Column(name = "armor_qualities")
    private List<ArmorQualities> armorQualities;
}
