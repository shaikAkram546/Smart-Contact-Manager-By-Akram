package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.exceptions;

public class ResourcesNotFound extends RuntimeException {
    public ResourcesNotFound(String mess) {
        super(mess);
    }

    public ResourcesNotFound() {
        super("There is No user in the dataBase");
    }
}
