package com.teus.projectrpg.skirmish.dto;

import lombok.Data;

@Data
public class SkirmishGroupDto {
    private Long id;
    private String name;
    private int advantages;
    private int colorR;
    private int colorG;
    private int colorB;
}
