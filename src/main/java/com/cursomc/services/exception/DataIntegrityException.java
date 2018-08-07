package com.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String mensagem) {
		super(mensagem);
	}
	
	public DataIntegrityException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
}