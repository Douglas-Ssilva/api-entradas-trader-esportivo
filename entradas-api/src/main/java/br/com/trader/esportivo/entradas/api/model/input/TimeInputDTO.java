package br.com.trader.esportivo.entradas.api.model.input;

import javax.validation.constraints.NotNull;

import br.com.trader.esportivo.entradas.core.validation.NomesValidation;
import br.com.trader.esportivo.entradas.domain.model.ContinenteEnum;
import br.com.trader.esportivo.entradas.domain.model.PaisEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NomesValidation
public class TimeInputDTO extends NomeInputDTO {
	
//	@NotNull
	private PaisEnum pais;

	@NotNull
	private ContinenteEnum continente;

}
