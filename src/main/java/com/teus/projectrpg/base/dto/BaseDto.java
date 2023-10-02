package com.teus.projectrpg.base.dto;

import lombok.Data;

@Data
public class BaseDto<T> {
    protected Long id;
    protected T name;
    protected String nameTranslation;

    public BaseDto() {
    }

    public BaseDto(Long id, T name, String nameTranslation) {
        this.id = id;
        this.name = name;
        this.nameTranslation = nameTranslation;
    }
}
