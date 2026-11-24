package com.teus.projectrpg.skirmish.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The only place that draws random numbers, so tests can replace it with fixed rolls.
 */
@Component
public class DiceRoller {

    /**
     * A d100 roll: 1 to 100, where 100 is the "00" result.
     */
    public int rollD100() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
