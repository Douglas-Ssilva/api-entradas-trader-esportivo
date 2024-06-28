package br.com.trader.esportivo.entradas.domain.infrastructure.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Banca_;
import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.model.Entrada_;
import br.com.trader.esportivo.entradas.domain.model.Metodo;
import br.com.trader.esportivo.entradas.domain.model.Metodo_;

public class EntradaSpecs {
	
	public static Specification<Entrada> usandoFiltro(Long bancaId) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
			Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
			predicates.add(builder.equal(joinBanca.get(Banca_.id), bancaId));
			query.orderBy(builder.desc(root.get(Entrada_.data)));
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
