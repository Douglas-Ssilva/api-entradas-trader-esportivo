package br.com.trader.esportivo.entradas.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trader.esportivo.entradas.domain.model.ContinenteEnum;
import br.com.trader.esportivo.entradas.domain.model.PaisEnum;
import br.com.trader.esportivo.entradas.domain.repository.RepositoryCommon;

@Service
public abstract class CommonService {
	
	@Autowired
	private RepositoryCommon repository;
	
	public Optional<String> entityAlreadyExists(Class<?> clazz, Long id, String name, PaisEnum pais, ContinenteEnum continente) {
		return this.repository.entityAlreadyExists(clazz, id, name, pais, continente);
	}
	
	public Optional<String> entityAlreadyExists(Class<?> clazz, Long id, String name) {
		return this.repository.entityAlreadyExists(clazz, id, name);
	}
	
}
