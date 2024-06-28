package br.com.trader.esportivo.entradas.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserDTO {
	
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	private String email;

}
