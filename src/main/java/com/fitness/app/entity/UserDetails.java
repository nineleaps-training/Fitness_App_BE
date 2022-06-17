package com.fitness.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("user_details")
public class UserDetails {

    @Id
    @Field
    private String email;

    @Field
    private String gender;

    @Field
    private String fullAddress;

    @Field
    private String city;

    @Field
    private int postal;

    @Field
	private String pdf_fileName;
    
    @Field
	private String pdf_fileType;

    @Field
    private byte[] pdf_data;

    @Field
	private String qr_fileName;
    
    @Field
	private String qr_fileType;

    @Field
    private byte[] qr_data;

}
