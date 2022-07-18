package com.fitness.app.exceptions;

import org.springframework.dao.DataAccessException;

public class DataNotFoundException extends DataAccessException {

    public DataNotFoundException(String msg)
    {
        super(msg);
    }
}
