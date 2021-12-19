package com.teus.projectrpg.controller.exception;

import org.hibernate.PropertyValueException;

public class FieldCannotBeNullException extends RuntimeException{
    public FieldCannotBeNullException(PropertyValueException cause) {
        super("Field: '" + cause.getPropertyName() + "' cannot be null");
    }
}
