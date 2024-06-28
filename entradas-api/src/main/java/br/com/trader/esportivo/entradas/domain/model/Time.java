package br.com.trader.esportivo.entradas.domain.model;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TIME")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Time implements Serializable { //Serializable por causa do SerializationUtils#clone

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String nome;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "pais_enum", length = 50, nullable = false)
	private PaisEnum pais;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "continente_enum", length = 50, nullable = false)
	private ContinenteEnum continente;
	
	@UpdateTimestamp
	@Column(name = "data_atualizacao")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany(mappedBy = "times")
	private Set<Campeonato> campeonatos;
	
	@Transient
	private List<String> nomesTimes;
	
	//Criado pois na conversão do ModelMapper estava dando erro ao acessar direto a propriedade
	public String getLabelContinenteString() {
		return nonNull(getContinente()) ? getContinente().getLabel() : null;
	}
	
	public String getLabelPaisString() {
		return nonNull(getPais()) ? getPais().getLabel() : null;
	}
	
}
