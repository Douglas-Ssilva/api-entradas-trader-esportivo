package br.com.trader.esportivo.entradas.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "APORTE_SAQUE")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaqueAporte {
	
	public SaqueAporte() {
	}
	
	public SaqueAporte(Banca banca, BigDecimal valor) {
		this.banca = banca;
		this.saqueOuAporte = valor;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_banca", nullable = false)
	private Banca banca;
	
	@NotNull
	@Column(name = "saque_aporte")
	private BigDecimal saqueOuAporte;

}
