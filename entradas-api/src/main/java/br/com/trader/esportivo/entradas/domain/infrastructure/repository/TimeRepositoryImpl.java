package br.com.trader.esportivo.entradas.domain.infrastructure.repository;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import br.com.trader.esportivo.entradas.domain.model.ContinenteEnum;
import br.com.trader.esportivo.entradas.domain.model.PaisEnum;
import br.com.trader.esportivo.entradas.domain.repository.RepositoryCommon;

@Repository
public class TimeRepositoryImpl implements RepositoryCommon {
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Optional<String> entityAlreadyExists(Class<?> clazz,  Long id, String name) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(String.class);
		var root = query.from(clazz);
		
		query.select(root.get("nome"));
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(cb.like(cb.lower(root.get("nome")),	StringUtils.stripAccents(name).toLowerCase().trim()));
		if(nonNull(id)) {
			predicates.add(cb.notEqual(root.get("id"), id));
		}
		
		query.where(predicates.toArray(new Predicate[0]));
		
		var list = this.manager.createQuery(query).getResultList();
		
		if (isEmpty(list)) {
			return Optional.empty();
		}
		return Optional.ofNullable(list.get(0));
	}
	
	@Override
	public Optional<String> entityAlreadyExists(Class<?> clazz,  Long id, String name, PaisEnum pais, ContinenteEnum continente) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(String.class);
		var root = query.from(clazz);
		
		query.select(root.get("nome"));
		
		var predicates = new ArrayList<>();
		
		predicates.add(cb.like(cb.lower(root.get("nome")),	"%" + StringUtils.stripAccents(name).toLowerCase().trim() + "%"));
		if(nonNull(id)) {
			predicates.add(cb.notEqual(root.get("id"), id));
		}
		if(nonNull(pais)) {
			predicates.add(cb.equal(root.get("pais"), pais));
		}
		if(nonNull(continente)) {
			predicates.add(cb.equal(root.get("continente"), continente));
		}
		query.where(predicates.toArray(new Predicate[0]));
		
		var list = this.manager.createQuery(query).getResultList();
		
		if (isEmpty(list)) {
			return Optional.empty();
		}
		return Optional.ofNullable(list.get(0));
	}
	
}
