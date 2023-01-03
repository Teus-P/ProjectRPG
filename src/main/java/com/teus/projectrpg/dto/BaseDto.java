package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto <T, E extends BaseEntity<T>>{
    protected Long id;
    protected T name;
    protected String nameTranslation;

    public BaseDto(){}

    public BaseDto(E entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
