package br.com.trader.esportivo.entradas.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "campeonatos")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonInclude(Include.NON_NULL)
public class CampeonatoDTO  extends RepresentationModel<CampeonatoDTO>{
	
	public CampeonatoDTO() {
	}
	
	public CampeonatoDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	private String pais;

	private String continente;
	
	private Integer totalTimes;
	
	private Boolean ativo;
}
