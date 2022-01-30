package com.teus.projectrpg.entity.armor;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
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
