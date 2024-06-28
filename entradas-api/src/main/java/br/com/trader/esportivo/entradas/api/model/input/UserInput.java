package br.com.trader.esportivo.entradas.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserInput {
	
	@NotBlank
	@Size(min = 4, max = 250)
	private String nome;
	
	@NotBlank
	@Email
	@Size(max = 250)
	private String email;

}
