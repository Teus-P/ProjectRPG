package com.teus.projectrpg.spell.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.spell.type.SpellGroupType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "spell_group")
public class SpellGroupEntity extends BaseEntity<SpellGroupType> {

    @Column
    @Enumerated(EnumType.STRING)
    private SpellGroupType name;
}
