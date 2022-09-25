package com.teus.projectrpg.exception;

public class ElementNotFoundException extends RuntimeException{

    public ElementNotFoundException(Long id) {
        super("Could not find element with id " + id);
    }
}
