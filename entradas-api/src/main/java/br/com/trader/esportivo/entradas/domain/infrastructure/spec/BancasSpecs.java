package br.com.trader.esportivo.entradas.domain.infrastructure.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Banca_;
import br.com.trader.esportivo.entradas.domain.model.User;

public class BancasSpecs {
	
	public static Specification<Banca> usandoFiltro(User user, String nome) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			predicates.add(builder.equal(root.get(Banca_.user), user));
			if(StringUtils.isNoneBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(Banca_.nome)), "%" + nome.toUpperCase() + "%"));
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
