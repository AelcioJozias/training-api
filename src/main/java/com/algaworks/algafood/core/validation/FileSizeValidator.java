package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

// isso indica a interace/anotação validadora. E o segundo argumento, o tipo que vai ser anotado, que é o MultipartFile
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private DataSize maxSize;
	
	// esse método vai inicializar as variáveis da classe
	@Override
	public void initialize(FileSize constraintAnnotation) {
		// transforma a string passada para um DataSize
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	// aqui é o ponto que fazemos a nossa constraint para validar
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		// compara se o tamanho de bytes do arquivo é menor ou igual ao permitido ao que foi atribuído na anotação
		return value == null || value.getSize() <= this.maxSize.toBytes();
	}

}
