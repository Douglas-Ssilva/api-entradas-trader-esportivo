package br.com.trader.esportivo.entradas.domain.model;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "BANCA")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Banca implements Serializable { //Serializable por causa do SerializationUtils#clone 

	private static final long serialVersionUID = 1L;

	public Banca() {
	
	}
	
	public Banca(Long bancaId) {
		this.id = bancaId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String nome;
	
	@NotNull
	@DecimalMin(value = "0.0")
	private BigDecimal valor;
	
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	private Boolean principal;
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "banca")
	private List<SaqueAporte> saqueAporte = new ArrayList<>();
	
	@Transient
	private BigDecimal saque;
	
	@Transient
	private BigDecimal aporte;
	
	@Transient
	private BigDecimal totalSaque;
	
	@Transient
	private BigDecimal totalAporte;
	
	public boolean flagPrincipalIsSelecionada() {
		return nonNull(getPrincipal()) && Boolean.TRUE.equals(getPrincipal());
	}

	public void sacar(BigDecimal valorSaque) {
		setValor(getValor().subtract(valorSaque));
		getSaqueAporte().add(new SaqueAporte(this, valorSaque.negate()));
	}

	public void aportar(BigDecimal aporte) {
		setValor(getValor().add(aporte));
		getSaqueAporte().add(new SaqueAporte(this, aporte));
	}

	public void atualizarValor(BigDecimal lucroPrejuizo) {
		setValor(getValor().add(lucroPrejuizo));
	}

	

}
