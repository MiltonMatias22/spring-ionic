package com.cursomc.domain;

import javax.persistence.Entity;

import com.cursomc.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("pagamentoCartao")
public class PagamentoCartao extends Pagamento{
	
	private static final long serialVersionUID = 1L;

	private Integer numeroParcelas;

	public PagamentoCartao() {
		super();
	}

	public PagamentoCartao(Integer id, EstadoPagamento tipoPagamento, Pedido pedido, Integer numeroParcelas) {
		super(id, tipoPagamento, pedido);
		this.numeroParcelas = numeroParcelas;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	
	
}
