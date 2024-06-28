package br.com.trader.esportivo.entradas.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDTO extends UserInput {

	@NotBlank
	@Size(min = 4, max = 100)
	private String senha;
	
}
