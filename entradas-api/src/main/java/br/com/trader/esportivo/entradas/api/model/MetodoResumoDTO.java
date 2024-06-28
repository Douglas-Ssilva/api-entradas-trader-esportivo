package br.com.trader.esportivo.entradas.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetodoResumoDTO {
	
	private Long id;
	
	private String nome;
	
	private Boolean exigirPreenchimentoFlagMandanteVisitante;
	
	private Boolean exigirPreenchimentoFlagGolContraFavor;
	
	private Boolean exigirPreenchimentoFlagRedCard;

}
