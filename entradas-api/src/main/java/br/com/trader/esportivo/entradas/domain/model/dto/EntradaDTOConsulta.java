package br.com.trader.esportivo.entradas.domain.model.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.trader.esportivo.entradas.api.helper.EntradaUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class EntradaDTOConsulta {
	

	public EntradaDTOConsulta() {
	}
	
	public EntradaDTOConsulta(String campeonato, BigDecimal total) {
		this.campeonato = campeonato;
		this.total = total;
	}
	
	public EntradaDTOConsulta(BigDecimal total, String time, Long timeId) {
		this.time = time;
		this.total = total;
		this.timeId = timeId;
	}
	
	public EntradaDTOConsulta(String metodo, BigDecimal total, BigDecimal valorBanca, BigDecimal valorStake) {
		this.metodo = metodo;
		this.total = total;
		this.percentualBanca = EntradaUtils.getPercent(total, valorBanca);
		this.percentualStack = EntradaUtils.getPercent(total, valorStake);
	}
	
	private Long timeId;

	private String campeonato;

	private String time;

	private String metodo;

	private BigDecimal total;

	private BigDecimal percentualBanca;

	private BigDecimal percentualStack;

}
