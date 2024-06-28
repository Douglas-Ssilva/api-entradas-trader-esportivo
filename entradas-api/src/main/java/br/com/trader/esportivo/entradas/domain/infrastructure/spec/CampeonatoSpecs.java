package br.com.trader.esportivo.entradas.domain.infrastructure.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Campeonato_;

//Fábrica de Specificacion, assim não preciso dar new em cada Spec RestauranteComFreteGratisSpec
public class CampeonatoSpecs {
	
	private CampeonatoSpecs() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Specification<Campeonato> usandoFiltro(String nome) {
		return (root, query, builder) -> {
			//O Pageable faz um select count pra verificar quantos elementos existem no total, e se não colocarmos essa condição lança excação:  query specified join fetching, 
			//but the owner of the fetched association was not present in the select list pois não faz sentido count com fetch que é o retorno do select
//			if (Campeonato.class.equals(query.getResultType())) {
//				root.fetch("cliente");
//				root.fetch("restaurante").fetch("cozinha");//evitando problema do N + 1
//			}
			
			var predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(nome)) {
				predicates.add(builder.like(builder.upper(root.get(Campeonato_.nome)), "%" + nome.toUpperCase() + "%"));
			}
			query.orderBy(builder.asc(root.get(Campeonato_.nome)));
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
