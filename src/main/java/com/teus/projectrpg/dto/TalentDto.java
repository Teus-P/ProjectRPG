package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.talent.TalentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TalentDto extends BaseDto<TalentType> {
    private String maxLevel;
}
