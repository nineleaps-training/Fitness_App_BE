package com.fitness.app.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("images")
public class ImageOperationDoc {

	private String id;
	private String fileName;
	private String fileType;
	private byte[] data;
}
