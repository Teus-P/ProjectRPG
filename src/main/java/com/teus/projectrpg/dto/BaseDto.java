package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto <T, E extends BaseEntity>{
    protected Long id;
    protected T name;

    public BaseDto(){}

    public BaseDto(E entity) {
        this.id = entity.getId();
        this.name = (T) entity.getName();
    }
}
