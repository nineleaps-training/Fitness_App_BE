package com.fitness.app.exceptions;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;

public class UserNotFoundException extends NamingException {
    public UserNotFoundException(String msg)
    {
        super(msg);
    }



}


