package com.teus.projectrpg.skirmish.dto;

import lombok.Data;

import java.util.List;

@Data
public class EndTurnCheckDto {

    private int roundNumber;
    private List<TestDto> tests;
}
