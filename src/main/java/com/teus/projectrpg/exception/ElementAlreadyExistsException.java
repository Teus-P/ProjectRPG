package com.teus.projectrpg.exception;

public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException(String elementName) {
        super("Element with name '" + elementName + "' already exists");
    }
}
