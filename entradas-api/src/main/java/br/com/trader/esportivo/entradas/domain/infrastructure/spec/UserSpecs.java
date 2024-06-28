package br.com.trader.esportivo.entradas.domain.infrastructure.spec;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.trader.esportivo.entradas.domain.model.User;
import br.com.trader.esportivo.entradas.domain.model.User_;

public class UserSpecs {

	public static Specification<User> usandoFiltro(String nome) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<>();
			
			if (isNotBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(User_.nome)), "%" + nome.trim().toUpperCase() + "%"));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
