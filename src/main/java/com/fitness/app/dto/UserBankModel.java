package com.fitness.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBankModel {
	
	
	    private String email;
	   
	    private String name;
	   
	    private String bankName;
	    
	    private String branchName;
	    
	    private Long accountNumber;
	    
	    private String bankIFSC;
	    
	    private String schedule;

}
