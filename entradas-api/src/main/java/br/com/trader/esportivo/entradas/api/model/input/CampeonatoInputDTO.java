package br.com.trader.esportivo.entradas.api.model.input;

import br.com.trader.esportivo.entradas.core.validation.NomesValidation;
import br.com.trader.esportivo.entradas.domain.model.ContinenteEnum;
import br.com.trader.esportivo.entradas.domain.model.PaisEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NomesValidation
public class CampeonatoInputDTO extends NomeInputDTO {

	private PaisEnum pais;

	private ContinenteEnum continente;
	
	private Integer totalTimes;
	
	private boolean ativo;
	
}
