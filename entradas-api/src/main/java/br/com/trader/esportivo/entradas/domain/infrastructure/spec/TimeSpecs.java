package br.com.trader.esportivo.entradas.domain.infrastructure.spec;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Campeonato_;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.model.Time_;

public class TimeSpecs {

	public static Specification<Time> usandoFiltro(Campeonato campeonato, String nome) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			var joinCampeonato = root.join(Time_.campeonatos);
			predicates.add(builder.equal(joinCampeonato, campeonato));
			if (isNotBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(Time_.nome)), "%" + nome.trim().toUpperCase() + "%"));
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<Time> findTimesNaoVinculadosAo(Campeonato campeonato, String nome) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			
			if (nonNull(campeonato.getPais())) {
				predicates.add(builder.equal(root.get(Time_.pais), campeonato.getPais()));
			}
			
			if (nonNull(campeonato.getContinente())) {
				predicates.add(builder.equal(root.get(Time_.continente), campeonato.getContinente()));
			}
			
			if (isNotEmpty(campeonato.getTimes())) {
				predicates.add(builder.not(root.in(campeonato.getTimes())));
			}
			
			if (isNotBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(Time_.nome)), "%" + nome.trim().toUpperCase() + "%"));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<Time> usandoFiltro(String nome) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			
			if (isNotBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(Time_.nome)), "%" + nome.trim().toUpperCase() + "%"));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<Time> findTimesCandidatos(Campeonato campeonato, String nome) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			
			if (nonNull(campeonato.getPais())) {
				predicates.add(builder.equal(root.get(Time_.pais), campeonato.getPais()));
			}
			
			if (nonNull(campeonato.getContinente())) {
				predicates.add(builder.equal(root.get(Time_.continente), campeonato.getContinente()));
			}
			
			if (isNotBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(Time_.nome)), "%" + nome.trim().toUpperCase() + "%"));
			}
			query.orderBy(builder.asc(root.get(Time_.nome)));
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
