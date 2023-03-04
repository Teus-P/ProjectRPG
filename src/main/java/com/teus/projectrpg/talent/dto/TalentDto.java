package com.teus.projectrpg.talent.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.talent.type.TalentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TalentDto extends BaseDto<TalentType> {
    private String maxLevel;
}
