package com.teus.projectrpg.entity.character;

import com.teus.projectrpg.entity.talent.TalentEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_talent")
public class CharacterTalentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @ManyToOne
    @JoinColumn(name = "talent_id", nullable = false)
    private TalentEntity talent;

    @Column(name = "value")
    private int value;
}
