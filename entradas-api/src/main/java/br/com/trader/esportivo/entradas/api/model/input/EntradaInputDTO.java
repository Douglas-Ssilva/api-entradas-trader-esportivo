package br.com.trader.esportivo.entradas.api.model.input;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.trader.esportivo.entradas.domain.model.ResultadoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@EntradaPossuiFlags
public class EntradaInputDTO {
	
	@NotNull
	private BigDecimal valor;
	
	private BigDecimal lucroPrejuizo;
	
//	private BigDecimal valorGolFavor;
//	
//	private BigDecimal valorGolContra;
//	
//	private BigDecimal valorRedCard;
	
	@NotNull
	@Valid
	private CampeonatoInputId campeonato;
	
	@NotNull
	private Long mandanteIdentificador;
	
	@NotNull
	private Long visitanteIdentificador;
	
	@NotBlank
	private String timeMandante;
	
	@NotBlank
	private String timeVisitante;
	
	@NotNull
	@Valid
	private MetodoInputId metodo;
	
	private LocalDate data;

	private ResultadoEnum resultadoHoraEntrada;
	
	private String comentario;
	
	private BigDecimal odd;
	
	private Boolean apostaAFavorMandante;
	
	private Boolean apostaAFavorVisitante;

	private Boolean redCard;
	
	private Boolean golAFavor;
	
	private Boolean golContra;
	
	private Boolean maisGolsFavor;
	
	private Boolean maisGolsContra;
}
