package br.com.trader.esportivo.entradas.domain.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntradaDTOEstatistica {
	
	private List<EntradaDTODatas> entradas;
	private BigDecimal percentualBanca;

}
