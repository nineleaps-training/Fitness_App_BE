package com.fitness.app.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "gym_collection")
public class GymClass {

    @Id
    @Field
    private String id;
    @Field
    private String email;
    @Field
    private String name;
    @Field
    private List<String> workout;
    @Field
    private Long contact;
    @Field
    private Double rate;
    @Field
    private int capacity;
}
