package com.teus.projectrpg.entity;

import com.teus.projectrpg.type.BodyLocalizationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "body_localization")
public class BodyLocalizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_localization")
    private BodyLocalizationType bodyLocalization;
}