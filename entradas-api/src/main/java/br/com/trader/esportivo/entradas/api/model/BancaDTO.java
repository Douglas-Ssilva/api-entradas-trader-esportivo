package br.com.trader.esportivo.entradas.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancaDTO {
	
	private Long id;
	
	private String nome;
	
	private BigDecimal valor;
	
	private BigDecimal totalSaque;
	
	private BigDecimal totalAporte;

	private boolean principal;
}
