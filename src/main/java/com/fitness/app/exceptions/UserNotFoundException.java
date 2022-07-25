package com.fitness.app.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

import javax.naming.NamingException;

public class UserNotFoundException extends  BadCredentialsException {
    public UserNotFoundException(String msg)
    {
        super(msg);
    }

}


