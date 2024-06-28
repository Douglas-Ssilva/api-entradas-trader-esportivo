package br.com.trader.esportivo.entradas.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "METODO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Metodo implements Serializable { //Serializable por causa do SerializationUtils#clone

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String nome;
	
	@NotNull
	@Column(name = "stake_default")
	@DecimalMin(value = "0.0")
	private BigDecimal stakeDefault;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_banca", nullable = false)
	private Banca banca;
	
	@Column(name = "exigir_preenchimento_flag_mandante_visitante")
	private Boolean exigirPreenchimentoFlagMandanteVisitante;
	
	@Column(name = "exigir_preenchimento_flag_gol_contra_favor")
	private Boolean exigirPreenchimentoFlagGolContraFavor;
	
	@Column(name = "exigir_preenchimento_flag_red_card")
	private Boolean exigirPreenchimentoFlagRedCard;
	

}
