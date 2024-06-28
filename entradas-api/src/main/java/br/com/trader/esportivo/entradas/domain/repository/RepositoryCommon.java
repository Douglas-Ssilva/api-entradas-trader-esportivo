package br.com.trader.esportivo.entradas.domain.repository;

import java.util.Optional;

import br.com.trader.esportivo.entradas.domain.model.ContinenteEnum;
import br.com.trader.esportivo.entradas.domain.model.PaisEnum;

public interface RepositoryCommon {
	
	Optional<String> entityAlreadyExists(Class<?> clazz, Long id, String name, PaisEnum pais, ContinenteEnum continente);
	
	Optional<String> entityAlreadyExists(Class<?> clazz, Long id, String name);

}
