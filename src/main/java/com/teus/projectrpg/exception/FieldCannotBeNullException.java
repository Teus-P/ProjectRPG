package com.teus.projectrpg.exception;

import org.hibernate.PropertyValueException;

public class FieldCannotBeNullException extends RuntimeException{
    public FieldCannotBeNullException(PropertyValueException cause) {
        super("Field: '" + cause.getPropertyName() + "' cannot be null");
    }
}
