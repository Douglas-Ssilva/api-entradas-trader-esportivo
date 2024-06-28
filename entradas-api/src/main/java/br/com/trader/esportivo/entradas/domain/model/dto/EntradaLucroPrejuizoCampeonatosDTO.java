package br.com.trader.esportivo.entradas.domain.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntradaLucroPrejuizoCampeonatosDTO {
	
	private List<EntradaCampeonatoDTO> campeonatosMaisLucrativos;
	private List<EntradaCampeonatoDTO> campeonatosMenosLucrativos;

}
