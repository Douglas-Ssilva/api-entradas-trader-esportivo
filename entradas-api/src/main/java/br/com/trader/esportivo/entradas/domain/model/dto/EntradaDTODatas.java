package br.com.trader.esportivo.entradas.domain.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntradaDTODatas extends DadosGraficoDTO {
	
	private Long metodoId;
	
	public EntradaDTODatas(LocalDate data, Long metodoId, BigDecimal total) {
		super(data, total);
		this.metodoId = metodoId;
	}
	
}
