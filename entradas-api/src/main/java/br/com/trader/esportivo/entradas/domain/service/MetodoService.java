package br.com.trader.esportivo.entradas.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.trader.esportivo.entradas.domain.exception.MetodoNotFoundException;
import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Metodo;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.repository.MetodoRepository;

@Service
public class MetodoService extends CommonService {
	
	@Autowired
	private MetodoRepository repository;
	
	public Page<Metodo> findAll(Specification<Metodo> especification, Pageable pageable){
		return this.repository.findAll(especification, pageable);
	}

	public Metodo findById(Long metodoId, Long bancaId) {
		return this.repository.findByIdAndBancaId(metodoId, bancaId).orElseThrow(() -> new MetodoNotFoundException(metodoId));
	}

	@Transactional
	public Metodo save(Long bancaId, Metodo metodo) {
		var metodoOp = entityAlreadyExists(Metodo.class, metodo.getId(), metodo.getNome());
		if(metodoOp.isPresent()) {
			throw new NegocioException(String.format("O %s já está cadastrado na base de dados.", metodoOp.get()));
		}
		metodo.setBanca(new Banca(bancaId));
		return this.repository.save(metodo);
	}

	@Transactional
	public void delete(Banca banca) {
		this.repository.deleteBanca(banca);
	}

//	@Transactional
//	public Metodo save(Metodo metodo) {
//		return this.repository.save(metodo);
//	}

	@Transactional
	public void delete(Metodo metodo) {
		this.repository.delete(metodo);
	}

}
