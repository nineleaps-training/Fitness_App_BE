package com.fitness.app.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPerformanceModel {


    private String name;
    private String email;
    private String gym;
    private String vendor;
    private List<Integer> attendance;
    private Double rating;

}
