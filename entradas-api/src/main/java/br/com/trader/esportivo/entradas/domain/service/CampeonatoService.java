package br.com.trader.esportivo.entradas.domain.service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.trader.esportivo.entradas.domain.exception.CampeonatoNotFoundException;
import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.TimeSpecs;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.repository.CampeonatoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CampeonatoService extends CommonService {
	
	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	@Autowired
	private TimeService timeService;
	
	public Page<Campeonato> findAll(Specification<Campeonato> specification, Pageable pageable) {
		return this.campeonatoRepository.findAll(specification, pageable);
	}

	public Campeonato findById(Long id) {
		return this.campeonatoRepository.findById(id).orElseThrow(() -> new CampeonatoNotFoundException(id));
	}
	
	@Transactional
	public void update(Campeonato campeonato) {
		var nomeCampeonatoOp = entityAlreadyExists(Campeonato.class, campeonato.getId(), campeonato.getNome(), campeonato.getPais(), campeonato.getContinente());
		if(nomeCampeonatoOp.isPresent()) {
			throw new NegocioException(String.format("O %s já está cadastrado na base de dados.", nomeCampeonatoOp.get()));
		}
		this.campeonatoRepository.save(campeonato);
	}

	@Transactional
	public void save(Campeonato campeonato) {
		campeonato.getNomesTimes().forEach(nomeTime -> {
			var nomeCampeonatoOp = entityAlreadyExists(Campeonato.class, campeonato.getId(), nomeTime, campeonato.getPais(), campeonato.getContinente());
			if(nomeCampeonatoOp.isPresent()) {
				throw new NegocioException(String.format("O %s já está cadastrado na base de dados.", nomeCampeonatoOp.get()));
			}
		});
		
		
		campeonato.getNomesTimes().forEach(nomeTime -> {
			var c = SerializationUtils.clone(campeonato);
			c.setNome(nomeTime);
			c.setTimes(null);
			this.campeonatoRepository.save(c);
		});
	}

	@Transactional
	public void desvincularTime(Long campeonatoId, Long timeId) {
		var campeonato = findById(campeonatoId);
		var time = timeService.findById(timeId);
		campeonato.desvincularTime(time);
	}

	@Transactional
	public void vincularTime(Long campeonatoId, Long timeId) {
		var campeonato = findById(campeonatoId);
		var time = timeService.findById(timeId);
		if (campeonato.naoPossoAddTimes()) {
			throw new NegocioException(String.format("O time %s não pode ser adicionado pois o campeonato %s já está com o máximo de times vinculados", time.getNome(), campeonato.getNome()));
		}
		campeonato.vincularTime(time);
	}
	
	public Page<Time> findAllTimesVinculadosCampeonato(Long campeonatoId, String nome, Pageable pageable) {
		var campeonato = findById(campeonatoId);
		return this.timeService.findAllTimesVinculadosCampeonato(TimeSpecs.usandoFiltro(campeonato, nome), pageable);
	}

	public Page<Time> findTimesPodemParticiparCampeonatoEQueNaoEstaoVinculados(Long campeonatoId, String nome, Pageable pageable) {
		var campeonato = findById(campeonatoId);
		if (campeonato.possoVincularMaisTimes()) {
			return this.timeService.findTimesPodemParticiparCampeonatoEQueNaoEstaoVinculados(TimeSpecs.findTimesNaoVinculadosAo(campeonato, nome), pageable);
		}
		return new PageImpl<>(Collections.emptyList());
	}
	
	public Page<Time> findAllTimesCandidatos(Long campeonatoId, String nome, Pageable pageable) {
		var campeonato = findById(campeonatoId);
		return this.timeService.findAllTimesCandidatos(TimeSpecs.findTimesCandidatos(campeonato, nome), pageable);
	}
	
	public Optional<OffsetDateTime> findLastUpdate() {
		return this.campeonatoRepository.findLastUpdate();
	}

	@Transactional
	public void vincularTimes(Long campeonatoId, List<Long> idsTime) {
		var campeonato = findById(campeonatoId);
		if (campeonato.possoAddTimes(idsTime)) {
			var times = this.timeService.findAll(idsTime);
			campeonato.vincularTimes(times);
		}
	}

	@Transactional
	public void desvincularTimes(Long campeonatoId, List<Long> idsTime) {
		var campeonato = findById(campeonatoId);
		var times = this.timeService.findAll(idsTime); 
		campeonato.desvincularTimes(times);
	}

}
