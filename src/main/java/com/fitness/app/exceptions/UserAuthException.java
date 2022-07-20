package com.fitness.app.exceptions;

import javax.naming.AuthenticationException;

public class UserAuthException extends AuthenticationException {
    public UserAuthException(String msg)
    {
        super(msg);
    }
}
