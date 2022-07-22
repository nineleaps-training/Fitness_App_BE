package com.fitness.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
