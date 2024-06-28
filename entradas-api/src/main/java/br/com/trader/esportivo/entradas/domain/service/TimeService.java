package br.com.trader.esportivo.entradas.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.exception.TimeNotFoundException;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.repository.EntradaRepository;
import br.com.trader.esportivo.entradas.domain.repository.TimeRepository;

@Service
public class TimeService extends CommonService {
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private EntradaRepository entradaRepository;
	
	public Page<Time> findAllTimesVinculadosCampeonato(Specification<Time> specification, Pageable pageable) {
		return timeRepository.findAll(specification, pageable);
	}

	public Time findById(Long timeId) {
		return timeRepository.findById(timeId).orElseThrow(() -> new TimeNotFoundException(timeId));
	}

	public Page<Time> findTimesPodemParticiparCampeonatoEQueNaoEstaoVinculados(Specification<Time> specification, Pageable pageable) {
		return timeRepository.findAll(specification, pageable);
	}

	public Page<Time> findAllTimesCandidatos(Specification<Time> specification, Pageable pageable) {
		return timeRepository.findAll(specification, pageable);
	}

	public List<Time> findAll(List<Long> idsTime) {
		return this.timeRepository.findAllById(idsTime);
	}

	public Page<Time> findAll(Specification<Time> specification, Pageable pageable) {
		return timeRepository.findAll(specification, pageable);
	}

	public Optional<OffsetDateTime> findLastUpdate() {
		return this.timeRepository.findLastUpdate();
	}

	@Transactional
	public Time update(Time time, boolean temAlteracaoNome) {
		var nomeTimeOp = entityAlreadyExists(Time.class, time.getId(), time.getNome(), time.getPais(), time.getContinente());
		if(nomeTimeOp.isPresent()) {
			throw new NegocioException(String.format("O %s já está cadastrado na base de dados.", nomeTimeOp.get()));
		}
		var timeUpdate = this.timeRepository.save(time);
		if(temAlteracaoNome) {
			this.entradaRepository.updateNomeTime(timeUpdate);
		}
		return timeUpdate;
	}
	
	@Transactional
	public void save(Time time) {
		time.getNomesTimes().forEach(nomeTime -> {
			var nomeTimeOp = entityAlreadyExists(Time.class, time.getId(), nomeTime, time.getPais(), time.getContinente());
			if(nomeTimeOp.isPresent()) {
				throw new NegocioException(String.format("O %s já está cadastrado na base de dados.", nomeTimeOp.get()));
			}
		});
		
		time.getNomesTimes().forEach(nomeTime -> {
			var team = SerializationUtils.clone(time);
			team.setNome(nomeTime);
			this.timeRepository.save(team);
		});
	}

	@Transactional
	public void delete(Long timeId) {
		var time = findById(timeId);
		this.timeRepository.delete(time);
		this.timeRepository.flush();
	}

}
