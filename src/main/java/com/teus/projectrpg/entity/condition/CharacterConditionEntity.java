package com.teus.projectrpg.entity.condition;

import com.teus.projectrpg.entity.character.CharacterEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_condition")
public class CharacterConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id")
    private CharacterEntity character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condition_id")
    private ConditionEntity condition;

    @Column(name = "value")
    private int value;
}
