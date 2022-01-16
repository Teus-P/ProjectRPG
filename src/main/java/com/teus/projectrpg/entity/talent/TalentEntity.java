package com.teus.projectrpg.entity.talent;

import com.teus.projectrpg.type.talent.TalentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "talent")
public class TalentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TalentType name;

    @Column
    private String max;
}