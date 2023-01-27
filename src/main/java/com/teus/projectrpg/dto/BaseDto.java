package com.teus.projectrpg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto<T> {
    protected Long id;
    protected T name;
    protected String nameTranslation;

    public BaseDto() {
    }
}
