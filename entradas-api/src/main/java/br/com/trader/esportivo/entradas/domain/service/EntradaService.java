package br.com.trader.esportivo.entradas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.trader.esportivo.entradas.domain.exception.EntradaNotFoundException;
import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.repository.EntradaRepository;

@Service
public class EntradaService {
	
	@Autowired
	private EntradaRepository repository;

	@Autowired
	private CampeonatoService campeonatoService;
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private MetodoService metodoService;
	
	@Autowired
	private BancaService bancaService;
	
	public Entrada findById(Long entradaId, Long bancaId) {
		return this.repository.findById(entradaId, bancaId).orElseThrow(() -> new EntradaNotFoundException(entradaId));
	}
	
	public Page<Entrada> findAll(Specification<Entrada> especification, Pageable pageable) {
		return this.repository.findAll(especification, pageable);
	}

	@Transactional
	public void save(List<Entrada> entradas, Long bancaId) {
//		this.repository.detach(entrada.getMetodo());
		var mandante = this.timeService.findById(entradas.get(0).getMandanteIdentificador());
		var visitante = this.timeService.findById(entradas.get(0).getVisitanteIdentificador());
		var campeonato = this.campeonatoService.findById(entradas.get(0).getCampeonato().getId());
		var banca = this.bancaService.findById(bancaId);
		validarRegras(mandante, visitante, entradas.get(0).getCampeonato());
		
		entradas.forEach(entrada -> {
			var metodo = this.metodoService.findById(entrada.getMetodo().getId(), bancaId);
			entrada.setMetodo(metodo);
			entrada.setCampeonato(campeonato);
			updateBancaAndSaveEntrada(entrada, banca);
		});
	}

	private void updateBancaAndSaveEntrada(Entrada entrada, Banca banca) {
		banca.atualizarValor(entrada.getLucroPrejuizo());
		this.repository.save(entrada);
	}
	
	@Transactional
	public void delete(Long entradaId, Long bancaId) {
		var entrada = findById(entradaId, bancaId);
		this.repository.delete(entrada);
		this.repository.flush();
	}
	

	private void validarRegras(Time mandante, Time visitante, Campeonato campeonato) {
		if (mandante.equals(visitante)) {
			throw new NegocioException("O time mandante deve ser diferente do visitante.");
		}
//		Passamos a vincular times por meio de Pais e Continente, conforme campeonato. Pra cada campeonato vincular times é mais trabalho
//		if (campeonato.timesNaoPodemJogar(Arrays.asList(mandante, visitante))) {
//			throw new NegocioException("Os times não podem disputar esse campeonato.");
//		}
	}

}
