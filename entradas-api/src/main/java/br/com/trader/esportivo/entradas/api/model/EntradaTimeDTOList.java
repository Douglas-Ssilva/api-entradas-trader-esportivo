package br.com.trader.esportivo.entradas.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntradaTimeDTOList {
	
	private List<EntradaTimeDTO> timesMaisLucrativos;
	private List<EntradaTimeDTO> timesMenosLucrativos;

}
