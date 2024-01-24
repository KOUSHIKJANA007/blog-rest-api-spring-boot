package com.koushik.blog.exception;


public class ResourceNotFoundException extends RuntimeException {

	
	String recouceName;
	String fieldName;
	long fieldvalue;
	
	public ResourceNotFoundException(String recouceName, String fieldName, long fieldvalue) {
		super(String.format("%s not found with %s : %s",recouceName,fieldName,fieldvalue));
		this.recouceName = recouceName;
		this.fieldName = fieldName;
		this.fieldvalue = fieldvalue;
	}
	
	
}
