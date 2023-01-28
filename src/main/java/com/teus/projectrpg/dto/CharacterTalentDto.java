package com.teus.projectrpg.dto;

import lombok.Data;

@Data
public class CharacterTalentDto {
    private Long id;
    private TalentDto talent;
    private int value;
}
