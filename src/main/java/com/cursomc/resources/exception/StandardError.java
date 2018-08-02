package com.cursomc.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer Status;
	
	private String Mensagem;
	
	private Long timeStamp;

	public StandardError(Integer status, String mensagem, Long timeStamp) {
		super();
		Status = status;
		Mensagem = mensagem;
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getMensagem() {
		return Mensagem;
	}

	public void setMensagem(String mensagem) {
		Mensagem = mensagem;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
