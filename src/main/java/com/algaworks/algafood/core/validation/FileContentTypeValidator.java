package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;


public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private String [] typesAccepted;
	private List<String> typesAcceptedList;

	@Override
	public void initialize(FileContentType contentType) {
		this.typesAccepted = contentType.types();
		this.typesAcceptedList = Arrays.asList(this.typesAccepted);
	}
	
	@Override
	public boolean isValid(MultipartFile multiPartFile , ConstraintValidatorContext context) {
		return multiPartFile == null 
			|| typesAcceptedList.contains(multiPartFile.getContentType());
	}

}
