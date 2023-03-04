package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "body_localization")
public class BodyLocalizationEntity extends BaseEntity<BodyLocalizationType> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private BodyLocalizationType name;
}
