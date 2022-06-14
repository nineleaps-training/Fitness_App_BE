package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rows {
    @JsonProperty("elements")
    private Elements[] elements;

    public Elements[] getElements() {
        return elements;
    }

    public void setElements(Elements[] elements) {
        this.elements = elements;
    }

    
}
