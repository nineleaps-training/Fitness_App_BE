package com.fitness.app.qr;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("qrcode")
public class QRCode {

	private String id;
	private String qr_fileName;
	private String qr_fileType;
	private byte[] qr_data;
}
