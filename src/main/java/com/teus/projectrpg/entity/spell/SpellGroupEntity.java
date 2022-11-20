package com.teus.projectrpg.entity.spell;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.spell.SpellGroupType;
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
