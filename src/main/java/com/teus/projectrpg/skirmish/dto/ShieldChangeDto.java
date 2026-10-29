package com.teus.projectrpg.skirmish.dto;

/**
 * A shield that loses one point of its Shield value. {@code after == 0} means the shield is destroyed.
 */
public record ShieldChangeDto(String nameTranslation, int before, int after) {
}
