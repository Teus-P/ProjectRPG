package com.teus.projectrpg.entity.spell;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.spell.SpellType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "spell")
public class SpellEntity extends BaseEntity<SpellType> {

    @Column
    @Enumerated(EnumType.STRING)
    private SpellType name;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "spell_group_id", nullable = false)
    private SpellGroupEntity spellGroup;
}
