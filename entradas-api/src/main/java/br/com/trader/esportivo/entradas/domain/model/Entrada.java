package br.com.trader.esportivo.entradas.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ENTRADA")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entrada implements Serializable { //Serializable por causa do SerializationUtils#clone
	
	private static final long serialVersionUID = 1L;

	public Entrada() {
		super();
	}
	
	public Entrada(Long id, Metodo metodo, Boolean redCard, Boolean golAFavor, Boolean golContra, Boolean maisGolsContra, Boolean maisGolsFavor, BigDecimal lucroPrejuizo) {
		super();
		this.id = id;
		this.metodo = metodo;
		this.redCard = redCard;
		this.golAFavor = golAFavor;
		this.golContra = golContra;
		this.maisGolsContra = maisGolsContra;
		this.maisGolsFavor = maisGolsFavor;
		this.lucroPrejuizo = lucroPrejuizo;
	}
	
	public Entrada(Long metodoId, BigDecimal correcao) {
		super();
		this.metodo = new Metodo();
		this.metodo.setId(metodoId);
		this.correcao = correcao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotNull
	private BigDecimal valor;
	
	@NotNull
	@Column(name = "lucro_prejuizo")
	private BigDecimal lucroPrejuizo;
	
//	@CreationTimestamp
	private LocalDate data;
	
	@Column(name = "resultado_hora_entrada_enum")
	@Enumerated(EnumType.STRING)
	private ResultadoEnum resultadoHoraEntrada;
	
	@Size(max = 250)
	private String comentario;
	
	private BigDecimal odd;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_campeonato", nullable = false)
	private Campeonato campeonato;
	
	@NotNull
	@Column(name = "id_time_mandante", nullable = false)
	private Long mandanteIdentificador;
	
	@NotNull
	@Column(name = "time_mandante", nullable = false)
	private String timeMandante;
	
	@NotNull
	@Column(name = "id_time_visitante", nullable = false)
	private Long visitanteIdentificador;
	
	@NotNull
	@Column(name = "time_visitante", nullable = false)
	private String timeVisitante;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_metodo", nullable = false)
	private Metodo metodo;
	
	@Column(name = "aposta_favor_mandante")
	private Boolean apostaAFavorMandante;
	
	@Column(name = "aposta_favor_visitante")
	private Boolean apostaAFavorVisitante;
	
	@Column(name = "red_card")
	private Boolean redCard;
	
	@Column(name = "gol_favor")
	private Boolean golAFavor;
	
	@Column(name = "gol_contra")
	private Boolean golContra;
	
	@Column(name = "mais_gols_favor")
	private Boolean maisGolsFavor;
	
	@Column(name = "mais_gols_contra")
	private Boolean maisGolsContra;
	
	@Transient
	private BigDecimal correcao;
	
//	@Transient
//	private BigDecimal valorGolFavor;
//	
//	@Transient
//	private BigDecimal valorGolContra;
//	
//	@Transient
//	private BigDecimal valorRedCard;
	
}
