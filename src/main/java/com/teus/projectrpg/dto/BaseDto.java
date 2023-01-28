package com.teus.projectrpg.dto;

import lombok.Data;

@Data
public class BaseDto<T> {
    protected Long id;
    protected T name;
    protected String nameTranslation;
}
