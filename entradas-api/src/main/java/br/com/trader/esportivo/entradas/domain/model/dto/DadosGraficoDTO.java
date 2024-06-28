package br.com.trader.esportivo.entradas.domain.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DadosGraficoDTO {
	
	private LocalDate data;
	
	private BigDecimal total;

}
