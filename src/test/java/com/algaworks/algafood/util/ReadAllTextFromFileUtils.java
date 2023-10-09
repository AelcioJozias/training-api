package com.algaworks.algafood.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.algaworks.algafood.domain.exception.NegocioException;

public class ReadAllTextFromFileUtils {
	
	private static final String UTF_8 = "UTF-8";

	public static String getAllStringFile(String path) {
		
		File filePath = new File(path);
		verificaSeOarquivoExiste(path, filePath);
		
		try {
			InputStream inputStream = new FileInputStream(filePath);
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			inputStream.close();
			return new String(bytes, Charset.forName(UTF_8));
		} catch (IOException e) {
			throw new NegocioException("Erro ao processar o arquivo json");
		} 
		
	}

	private static void verificaSeOarquivoExiste(String path, File filePath) {
		if(!filePath.exists()) {
			throw new NegocioException("Não existe um json no caminho solicitado: " + path);
		}
	}

}
