package br.com.trader.esportivo.entradas.api.model.input;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NomeInputDTO {
	
	private String nome;
	
	private List<String> nomesTimes;
	
}
