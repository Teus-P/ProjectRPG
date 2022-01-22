package com.teus.projectrpg.entity.character;

import com.teus.projectrpg.entity.characteristic.CharacteristicEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_characteristic")
public class CharacterCharacteristicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @ManyToOne
    @JoinColumn(name = "characteristic_id", nullable = false)
    private CharacteristicEntity characteristic;

    @Column(name = "value")
    private int value;
}
