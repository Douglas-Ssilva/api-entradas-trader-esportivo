package br.com.trader.esportivo.entradas.domain.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.trader.esportivo.entradas.api.helper.EntradaUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntradaDadosGeraisDTO {
	
	private Long totalApostas;
	
	private BigDecimal lucroPrejuizo;
	
	private BigDecimal valorBanca;
	
	private BigDecimal percentualBanca = BigDecimal.ZERO;
	
	public EntradaDadosGeraisDTO(Long totalApostas, BigDecimal lucroPrejuizo, BigDecimal valorBanca) {
		super();
		this.totalApostas = totalApostas;
		this.lucroPrejuizo = lucroPrejuizo;
		this.valorBanca = valorBanca;
		if(Objects.nonNull(lucroPrejuizo)) {
			this.percentualBanca = EntradaUtils.getPercent(lucroPrejuizo, valorBanca.subtract(lucroPrejuizo.abs()));//% em cima do valor da banca - lucroPrejuizo
		}
	}
	

}
