package com.fitness.app.pdf;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("pdf")
public class PDF {

	private String id;
	private String pdf_fileName;
	private String pdf_fileType;
	private byte[] pdf_data;
}
