package com.teus.projectrpg.dto;

import lombok.Data;

import java.util.List;

@Data
public class EndTurnCheckDto {

    private int roundNumber;
    private List<TestDto> tests;

    public EndTurnCheckDto() {
    }

    public EndTurnCheckDto(int roundNumber, List<TestDto> tests) {
        this.roundNumber = roundNumber;
        this.tests = tests;
    }
}
