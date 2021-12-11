package com.teus.projectrpg.entity;

import com.teus.projectrpg.type.ArmorPenaltyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "armor_penalty")
public class ArmorPenaltyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "penalty")
    private ArmorPenaltyType armorPenaltyType;
}