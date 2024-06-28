package br.com.trader.esportivo.entradas.domain.model;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "CAMPEONATO")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Campeonato implements Serializable { //Serializable por causa do SerializationUtils#clone

	private static final long serialVersionUID = 1L;

	public Campeonato() {
		super();
	}
	
	public Campeonato(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "pais_enum", length = 50)
	private PaisEnum pais;

	@Enumerated(EnumType.STRING)
	@Column(name = "continente_enum", length = 50)
	private ContinenteEnum continente;
	
	@Column(name = "total_times", nullable = false)
	private Integer totalTimes;
	
	@UpdateTimestamp
	@Column(name = "data_atualizacao")
	private OffsetDateTime dataAtualizacao;
	
	private Boolean ativo;
	
	@ManyToMany
	@JoinTable(name = "TIME_CAMPEONATO", 
		joinColumns = @JoinColumn(name = "id_campeonato"), 
		inverseJoinColumns = @JoinColumn(name = "id_time"))
	private Set<Time> times = new HashSet<>();
	
	@Transient
	private List<String> nomesTimes;

	public void desvincularTime(Time time) {
		getTimes().remove(time);
	}

	public void vincularTime(Time time) {
		getTimes().add(time);
	}
	
	public boolean possoAddTimes() {
		return isNull(getTotalTimes()) || getTimes().size() < getTotalTimes(); 
	}
	
	public boolean naoPossoAddTimes() {
		return !possoAddTimes();
	}

	public boolean possoAddTimes(List<Long> idsTime) {
		return isNull(getTotalTimes()) || getTimes().size() + idsTime.size() <= getTotalTimes(); 
	}

	public void vincularTimes(List<Time> times) {
		getTimes().addAll(times);
	}

	public void desvincularTimes(List<Time> times) {
		getTimes().removeAll(times);
	}
	
	//Criado pois na conversão do ModelMapper estava dando erro ao acessar direto a propriedade
	public String getLabelContinenteString() {
		return nonNull(getContinente()) ? getContinente().getLabel() : null;
	}
	
	public String getLabelPaisString() {
		return nonNull(getPais()) ? getPais().getLabel() : null;
	}

	public boolean timesNaoPodemJogar(List<Time> timesParam) {
		return nonNull(getContinente()) && nonNull(getPais()) && !getTimes().containsAll(timesParam);
	}

	public boolean possoVincularMaisTimes() {
		return isNull(getTotalTimes()) || getTimes().size() < getTotalTimes(); 
	}
	
}
