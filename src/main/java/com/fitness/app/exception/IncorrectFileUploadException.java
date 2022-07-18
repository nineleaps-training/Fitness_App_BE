package com.fitness.app.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

public class IncorrectFileUploadException extends FileUploadException {

    // Initalizing Constructor
    public IncorrectFileUploadException(String msg) {
        super(msg);
    }

}
