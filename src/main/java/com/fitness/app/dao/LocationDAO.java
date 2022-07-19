package com.fitness.app.dao;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public interface LocationDAO {

    public String getDetails(@NotBlank @NotNull @RequestParam String address);

    public Map<String, List<String>> getAddress(@NotBlank @NotNull @RequestParam String latlng);
    
}