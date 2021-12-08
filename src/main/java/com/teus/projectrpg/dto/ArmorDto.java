package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.ArmorCategory;
import com.teus.projectrpg.type.ArmorPenalty;
import com.teus.projectrpg.type.ArmorQualities;
import com.teus.projectrpg.type.BodyLocalization;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArmorDto implements Serializable {
    private final Long id;
    private final String name;
    private final ArmorCategory armorCategory;
    private final List<BodyLocalization> bodyLocalization;
    private final Integer armorPoints;
    private final List<ArmorPenalty> penalty;
    private final List<ArmorQualities> armorQualities;
}
