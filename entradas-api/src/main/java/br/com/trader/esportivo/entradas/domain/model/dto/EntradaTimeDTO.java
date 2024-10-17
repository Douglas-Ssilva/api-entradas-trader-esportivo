package br.com.trader.esportivo.entradas.domain.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntradaTimeDTO {
	
	private String nome;
	
	private BigDecimal valor;
	
	private Long totalEntradas;	

}
