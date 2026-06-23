package com.teus.projectrpg.skirmish.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "skirmish_group")
public class SkirmishGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "advantages")
    private int advantages;

    @Column(name = "color_r")
    private int colorR;

    @Column(name = "color_g")
    private int colorG;

    @Column(name = "color_b")
    private int colorB;

    @JsonIgnore
    @OneToMany(mappedBy = "skirmishGroup")
    private List<SkirmishCharacterEntity> skirmishCharacters;
}
