package br.com.trader.esportivo.entradas.api.model;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetodoDTO {
	
	private Long id;
	
	private String nome;
	
	private BigDecimal stakeDefault;
	
	private Boolean exigirPreenchimentoFlagMandanteVisitante;
	
	private Boolean exigirPreenchimentoFlagGolContraFavor;
	
	private Boolean exigirPreenchimentoFlagRedCard;
	
}
