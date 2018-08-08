package com.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cursomc.domain.PagamentoBoleto;

@Service
public class BoletoService {
	
	//# Em uma situação real, trocar por uma chamada
	//# do webService que irá gerar o boleto do pogamento.
	
	public void preencherPagamentoBoleto(PagamentoBoleto pagamentoBoleto, Date instanteDoPedido) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(instanteDoPedido);

		cal.add(Calendar.DAY_OF_MONTH, 7);

		pagamentoBoleto.setDataVencimento(cal.getTime());

	}
}
