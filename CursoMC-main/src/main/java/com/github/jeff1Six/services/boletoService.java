package com.github.jeff1Six.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

import com.github.jeff1Six.dominio.PagamentoComBoleto;

@Configuration
public class boletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantedoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instantedoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
