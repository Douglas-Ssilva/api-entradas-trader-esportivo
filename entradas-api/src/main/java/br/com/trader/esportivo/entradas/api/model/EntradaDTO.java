package br.com.trader.esportivo.entradas.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntradaDTO {
	
	private Long id;
	
	private BigDecimal valor;
	
	private BigDecimal lucroPrejuizo;
	
	private BigDecimal odd;
	
	private boolean apostaAFavorMandante;
	
	private boolean apostaAFavorVisitante;
	
	private boolean redCard;
	
	private boolean golAFavor;
	
	private boolean golContra;
	
	private Boolean maisGolsFavor;
	
	private Boolean maisGolsContra;
	
	private LocalDate data;
	
	private CampeonatoResumoDTO campeonato;
	
	private Long mandanteIdentificador;
	
	private Long visitanteIdentificador;
	
	private String timeMandante;
	
	private String timeVisitante;
	
	private MetodoResumoDTO metodo;

}
