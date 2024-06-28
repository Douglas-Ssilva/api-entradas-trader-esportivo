package br.com.trader.esportivo.entradas.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "times")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TimeDTO extends RepresentationModel<TimeDTO>{
	
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	private String pais;

	private String continente;

}
