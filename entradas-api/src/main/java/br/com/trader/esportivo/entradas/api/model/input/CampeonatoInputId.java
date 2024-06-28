package br.com.trader.esportivo.entradas.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampeonatoInputId {
	
	@NotNull
	private Long id;

}
