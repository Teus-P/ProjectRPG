package com.teus.projectrpg.entity.armor;

import com.teus.projectrpg.type.armor.BodyLocalizationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "body_localization")
public class BodyLocalizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_localization")
    private BodyLocalizationType bodyLocalization;
}
