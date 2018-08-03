package com.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.cursomc.domain.enums.EstadoPagamento;

@Entity
public class PagamentoBoleto extends Pagamento{

	private static final long serialVersionUID = 1L;

	private Date dataVecimento;
	
	private Date dataPagamento;

	public PagamentoBoleto() {
		super();
	}

	public PagamentoBoleto(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Date dataVecimento,
			Date dataPagamento) {
		super(id, estadoPagamento, pedido);
		this.dataVecimento = dataVecimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVecimento() {
		return dataVecimento;
	}

	public void setDataVecimento(Date dataVecimento) {
		this.dataVecimento = dataVecimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
