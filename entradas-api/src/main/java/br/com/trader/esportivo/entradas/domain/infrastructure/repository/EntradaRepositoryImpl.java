package br.com.trader.esportivo.entradas.domain.infrastructure.repository;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.trader.esportivo.entradas.api.model.EntradaTimeDTO;
import br.com.trader.esportivo.entradas.api.model.EntradaTimeDTOList;
import br.com.trader.esportivo.entradas.api.model.MetodoPorEntradaDTO;
import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Banca_;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Campeonato_;
import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.model.Entrada_;
import br.com.trader.esportivo.entradas.domain.model.Metodo;
import br.com.trader.esportivo.entradas.domain.model.Metodo_;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaCampeonatoDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTOConsulta;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTODatas;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDadosGeraisDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaLucroPrejuizoCampeonatosDTO;
import br.com.trader.esportivo.entradas.domain.model.filter.EstatisticasFilter;
import br.com.trader.esportivo.entradas.domain.repository.CustomEntradaRepository;

@Repository
public class EntradaRepositoryImpl implements CustomEntradaRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<EntradaDTODatas> buscarEstatisticas(Long bancaId, EstatisticasFilter filter) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaDTODatas.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		
		var dtInicio = nonNull(filter.getDataInicio()) ? filter.getDataInicio() : getFirstDayOfCurrentMonth();
		var dtFim = nonNull(filter.getDataFim()) ? filter.getDataFim() : getLastDayOfCurrentMonth();
		
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, dtInicio, dtFim));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));

		query.select(cb.construct(EntradaDTODatas.class, 
				data, 
				cb.sum(root.get(Entrada_.lucroPrejuizo))));
		
		query.groupBy(data)
			.orderBy(cb.asc(data))
			.where(predicates.toArray(new Predicate[0]));
		
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Object[]> buscarPercentualSobreBanca(Long bancaId, EstatisticasFilter filter) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(Object[].class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		
		var dtInicio = nonNull(filter.getDataInicio()) ? filter.getDataInicio() : getFirstDayOfCurrentMonth();
		var dtFim = nonNull(filter.getDataFim()) ? filter.getDataFim() : getLastDayOfCurrentMonth();
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(root.get(Entrada_.data).as(LocalDate.class), dtInicio, dtFim));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		
		query.multiselect(cb.sum(root.get(Entrada_.lucroPrejuizo)), 
				joinBanca.get(Banca_.valor));
		
		query.groupBy(joinBanca)
			.where(predicates.toArray(new Predicate[0]));
		
		return this.manager.createQuery(query).getResultList();
	}
	
	@Override
	public EntradaLucroPrejuizoCampeonatosDTO findLucroPrejuizoCampeonatos(EstatisticasFilter filter, Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaCampeonatoDTO.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Join<Entrada, Campeonato> joinCampeonato = root.join(Entrada_.campeonato);
		
		Expression<LocalDate> data = root.get(Entrada_.data);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));

		query.select(cb.construct(EntradaCampeonatoDTO.class, 
				joinCampeonato.get(Campeonato_.nome), 
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				cb.count(root)));
		
		query.groupBy(joinCampeonato.get(Campeonato_.nome))
			.where(predicates.toArray(new Predicate[0]));
		
		var campeonatos = this.manager.createQuery(query).getResultList();
		
		var campeonatosMaisLucrativos = campeonatos.stream()
				.filter(ent -> ent.getValor().compareTo(BigDecimal.ZERO) > 0)
				.collect(Collectors.toList());
		campeonatosMaisLucrativos.sort(Comparator.comparing(EntradaCampeonatoDTO::getValor).reversed());
		
		var campeonatosMenosLucrativos = campeonatos.stream()
				.filter(ent -> ent.getValor().compareTo(BigDecimal.ZERO) < 0)
				.collect(Collectors.toList());
		campeonatosMenosLucrativos.sort(Comparator.comparing(EntradaCampeonatoDTO::getValor));
		
		return new EntradaLucroPrejuizoCampeonatosDTO(
				campeonatosMaisLucrativos.size() < 5 ? campeonatosMaisLucrativos : campeonatosMaisLucrativos.subList(0, 5),
				campeonatosMenosLucrativos.size() < 5 ? campeonatosMenosLucrativos : campeonatosMenosLucrativos.subList(0, 5));
	}
	

	@Override
	public List<EntradaDTOConsulta> findMetodosMaisLucrativos(EstatisticasFilter filter, Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaDTOConsulta.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		
		Expression<LocalDate> data = root.get(Entrada_.data);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));

		query.select(cb.construct(EntradaDTOConsulta.class, 
				joinMetodo.get(Metodo_.nome),
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				joinBanca.get(Banca_.valor),
				joinMetodo.get(Metodo_.stakeDefault)));
		
		query.groupBy(joinMetodo)
			.where(predicates.toArray(new Predicate[0]));
		
		return this.manager.createQuery(query).getResultList();
	}
	
	@Override
	public Page<EntradaDTOConsulta> findTimesMaisLucrativosCasa(Long bancaId, Pageable pageable) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaDTOConsulta.class);
		var root = query.from(Entrada.class);
		
		var predicates = new ArrayList<>();
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		predicates.add(cb.equal(root.get(Entrada_.apostaAFavorMandante), Boolean.TRUE));
		
		query.select(cb.construct(EntradaDTOConsulta.class, 
				cb.sum(root.get(Entrada_.lucroPrejuizo)), 
				root.get(Entrada_.TIME_MANDANTE),
				root.get(Entrada_.MANDANTE_IDENTIFICADOR)));
		
		query.groupBy(root.get(Entrada_.TIME_MANDANTE), root.get(Entrada_.MANDANTE_IDENTIFICADOR))
			.orderBy(cb.desc(cb.sum(root.get(Entrada_.lucroPrejuizo))))
			.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<EntradaDTOConsulta> typedQuery = this.manager.createQuery(query);
		tratarPaginacao(pageable, typedQuery);
		List<EntradaDTOConsulta> dtos = typedQuery.getResultList();
		
		return new PageImpl<>(dtos, pageable, buscarTotalRegistros(bancaId));
	}
	
	@Override
	public Page<EntradaDTOConsulta> findTimesMaisLucrativosFora(Long bancaId, Pageable pageable) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaDTOConsulta.class);
		var root = query.from(Entrada.class);
		
		var predicates = new ArrayList<>();
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		predicates.add(cb.equal(root.get(Entrada_.apostaAFavorVisitante), Boolean.TRUE));
		
		query.select(cb.construct(EntradaDTOConsulta.class, 
				cb.sum(root.get(Entrada_.lucroPrejuizo)), 
				root.get(Entrada_.TIME_VISITANTE),
				root.get(Entrada_.VISITANTE_IDENTIFICADOR)));
		
		query.groupBy(root.get(Entrada_.TIME_VISITANTE), root.get(Entrada_.VISITANTE_IDENTIFICADOR))
			.orderBy(cb.desc(cb.sum(root.get(Entrada_.lucroPrejuizo))))
			.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<EntradaDTOConsulta> typedQuery = this.manager.createQuery(query);
		tratarPaginacao(pageable, typedQuery);
		List<EntradaDTOConsulta> dtos = typedQuery.getResultList();
		
		return new PageImpl<>(dtos, pageable, buscarTotalRegistros(bancaId));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Entrada> findById(Long entradaId, Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(Entrada.class);
		var root = query.from(Entrada.class);
		
		root.fetch(Entrada_.campeonato);
		Fetch<Metodo, Banca> fetchBanca = root.fetch(Entrada_.metodo)
				.fetch(Metodo_.banca);
		Join<Metodo, Banca> joinBanca = (Join<Metodo, Banca>)fetchBanca;

		var predicates = new LinkedList<Predicate>();
		predicates.add(cb.equal(root.get(Entrada_.id), entradaId));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		
		query.where(predicates.toArray(new Predicate[0]));
		List<Entrada> list = this.manager.createQuery(query).getResultList();
		if (isEmpty(list)) {
			return Optional.empty();
		}
		return Optional.ofNullable(list.get(0));
	}
	
	@Override
	public void detach(Metodo entrada) {
		this.manager.detach(entrada);
	}
	
	@Override
	public Page<MetodoPorEntradaDTO> findAllMetodosPorEntrada(Long bancaId, EstatisticasFilter filter, Pageable pageable) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(MetodoPorEntradaDTO.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		var predicates = montarPredicatesComuns(bancaId, filter, cb, joinBanca, data);
		
		query.select(cb.construct(MetodoPorEntradaDTO.class, 
				joinMetodo.get(Metodo_.id),
				joinMetodo.get(Metodo_.nome),
				joinMetodo.get(Metodo_.exigirPreenchimentoFlagRedCard),
				joinMetodo.get(Metodo_.exigirPreenchimentoFlagGolContraFavor),
				cb.count(root),
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				joinMetodo.get(Metodo_.stakeDefault)));
		
		query.groupBy(joinMetodo.get(Metodo_.id), joinMetodo.get(Metodo_.nome))
			.orderBy(cb.desc(cb.count(root)))
			.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<MetodoPorEntradaDTO> typedQuery = this.manager.createQuery(query);
		tratarPaginacao(pageable, typedQuery);
		
		List<MetodoPorEntradaDTO> list = typedQuery.getResultList();
		
		preencherGolsFavorContraAndRedCard(list, bancaId, filter);
		preencherCorrecao(list, bancaId, filter);
		preencherTimesLucrativos(bancaId, list, filter);
		preencherDadosGrafico(bancaId, list, filter);
		
		return new PageImpl<>(list, pageable, buscarTotalRegistrosMetodosPorEntrada(bancaId, filter));
	}

	private void preencherCorrecao(List<MetodoPorEntradaDTO> metodos, Long bancaId, EstatisticasFilter filter) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(Entrada.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		Predicate predicateBanca = cb.equal(joinBanca.get(Banca_.id), bancaId);
		Predicate predicateData = cb.between(data, filter.getDataInicio(), filter.getDataFim());
		Predicate predicateMetodo = joinMetodo.get(Metodo_.id).in(metodos.stream().map(MetodoPorEntradaDTO::getId).collect(Collectors.toList()));
		
		Predicate predicateRedCard = cb.or(cb.notEqual(root.get(Entrada_.redCard), Boolean.TRUE), root.get(Entrada_.redCard).isNull());
		Predicate predicateGolFavor = cb.or(cb.notEqual(root.get(Entrada_.golAFavor), Boolean.TRUE), root.get(Entrada_.golAFavor).isNull());
		Predicate predicateGolContra = cb.or(cb.notEqual(root.get(Entrada_.golContra), Boolean.TRUE), root.get(Entrada_.golContra).isNull());		
		
		Predicate predicateMaisGolContraFavor = cb.and(cb.equal(root.get(Entrada_.maisGolsContra), Boolean.FALSE), cb.equal(root.get(Entrada_.maisGolsFavor), Boolean.FALSE));
		Predicate predicateGolFavorTrue = cb.and(cb.equal(root.get(Entrada_.golAFavor), Boolean.TRUE), cb.equal(root.get(Entrada_.golContra), Boolean.TRUE));
		
		Predicate predicateGolContraFavorFinal = cb.and(predicateBanca, predicateData, predicateMetodo, predicateGolContra, predicateGolFavor, predicateRedCard);
		Predicate predicateFinalMaisGolContraFavorFinal = cb.and(predicateMaisGolContraFavor, predicateGolFavorTrue);
		
		query.select(cb.construct(Entrada.class, 
				joinMetodo.get(Metodo_.id),
				cb.sum(root.get(Entrada_.lucroPrejuizo))));
		
		query.groupBy(joinMetodo.get(Metodo_.id)).where(cb.or(predicateGolContraFavorFinal, predicateFinalMaisGolContraFavorFinal));
		
		TypedQuery<Entrada> typedQuery = this.manager.createQuery(query);
		List<Entrada> list = typedQuery.getResultList();
		
		metodos.forEach(metodo -> {
			if(Boolean.TRUE.equals(metodo.getExigirPreenchimentoFlagGolContraFavor())) {
				var objOP = list.stream()
						.filter(met -> met.getMetodo().getId().equals(metodo.getId()))
						.findFirst();
				
				if(objOP.isPresent()){
					metodo.setCorrecao(objOP.get().getCorrecao());
				}
			}
		});
	}

	private void preencherGolsFavorContraAndRedCard(List<MetodoPorEntradaDTO> metodos, Long bancaId, EstatisticasFilter filter) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(Entrada.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		var predicates = montarPredicatesComuns(bancaId, filter, cb, joinBanca, data);

		predicates.add(joinMetodo.get(Metodo_.id).in(metodos.stream().map(MetodoPorEntradaDTO::getId).collect(Collectors.toList())));
		predicates.add(cb.or(
				cb.equal(root.get(Entrada_.redCard), Boolean.TRUE),
				cb.equal(root.get(Entrada_.golAFavor), Boolean.TRUE),
				cb.equal(root.get(Entrada_.golContra), Boolean.TRUE),
				cb.equal(root.get(Entrada_.maisGolsContra), Boolean.TRUE),
				cb.equal(root.get(Entrada_.maisGolsFavor), Boolean.TRUE)
				));

		query.select(cb.construct(Entrada.class, 
				root.get(Entrada_.id),
				root.get(Entrada_.metodo),
				root.get(Entrada_.redCard),
				root.get(Entrada_.golAFavor),
				root.get(Entrada_.golContra),
				root.get(Entrada_.maisGolsContra),
				root.get(Entrada_.maisGolsFavor),
				root.get(Entrada_.lucroPrejuizo)));
		
		query.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Entrada> typedQuery = this.manager.createQuery(query);
		List<Entrada> list = typedQuery.getResultList();
		
		metodos.forEach(metodo -> {
			if(Boolean.TRUE.equals(metodo.getExigirPreenchimentoFlagGolContraFavor())) {
				metodo.setGolsFavor(list.stream()
						.filter(entrada -> entrada.getMetodo().getId().equals(metodo.getId()))
						.filter(entrada -> Boolean.TRUE.equals(entrada.getGolAFavor()))
						.count());
				
				metodo.setTotalGolsFavor(list.stream()
						.filter(entrada -> entrada.getMetodo().getId().equals(metodo.getId()))
						.filter(entrada -> (Boolean.TRUE.equals(entrada.getGolAFavor()) && !Boolean.TRUE.equals(entrada.getGolContra())) || Boolean.TRUE.equals(entrada.getMaisGolsFavor()))
						.map(Entrada::getLucroPrejuizo)
						.reduce(BigDecimal.ZERO, BigDecimal::add)); 
				
				metodo.setGolsContra(list.stream()
						.filter(entrada -> entrada.getMetodo().getId().equals(metodo.getId()))
						.filter(entrada -> Boolean.TRUE.equals(entrada.getGolContra()))
						.count());
				
				metodo.setTotalGolsContra(list.stream()
						.filter(entrada -> entrada.getMetodo().getId().equals(metodo.getId()))
						.filter(entrada -> (Boolean.TRUE.equals(entrada.getGolContra()) && !Boolean.TRUE.equals(entrada.getGolAFavor())) || Boolean.TRUE.equals(entrada.getMaisGolsContra()))
						.map(Entrada::getLucroPrejuizo)
						.reduce(BigDecimal.ZERO, BigDecimal::add));
			}
			
			if(Boolean.TRUE.equals(metodo.getExigirPreenchimentoFlagRedCard())) {
				metodo.setRedCard(list.stream()
						.filter(entrada -> entrada.getMetodo().getId().equals(metodo.getId()))
						.filter(entrada -> Boolean.TRUE.equals(entrada.getRedCard()))
						.count());
				
			}
		});
	}

	private void preencherDadosGrafico(Long bancaId, List<MetodoPorEntradaDTO> metodos, EstatisticasFilter filter) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaDTODatas.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		predicates.add(joinMetodo.get(Metodo_.id).in(metodos.stream().map(MetodoPorEntradaDTO::getId).collect(Collectors.toList())));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));

		query.select(cb.construct(EntradaDTODatas.class, 
				data, 
				joinMetodo.get(Metodo_.id),
				cb.sum(root.get(Entrada_.lucroPrejuizo))));
		
		query.groupBy(data, joinMetodo.get(Metodo_.id))
			.orderBy(cb.asc(data))
			.where(predicates.toArray(new Predicate[0]));
		
		List<EntradaDTODatas> list = this.manager.createQuery(query).getResultList();
		
		Map<Long, List<EntradaDTODatas>> mapDadosGrafico = list.stream().collect(Collectors.groupingBy(EntradaDTODatas::getMetodoId));
		
		mapDadosGrafico.forEach((key, value) -> {
			MetodoPorEntradaDTO metodoPorEntradaDTO = metodos.stream().filter(metodo -> metodo.getId().equals(key)).findFirst().get();
			metodoPorEntradaDTO.setDadosGrafico(value);
		});
	}
	
	public void preencherTimesLucrativos(Long bancaId, List<MetodoPorEntradaDTO> metodos, EstatisticasFilter filter) {
		var timesCasa = findEntradaPorTime(bancaId, metodos, filter, false, true);
		var timesFora = findEntradaPorTime(bancaId, metodos, filter, true, false);
		
		timesCasa.forEach(mandante -> {
			Optional<EntradaTimeDTO> timeOp = mandanteIsListTimesVisitantes(timesFora, mandante);
			if(timeOp.isPresent()) {
				mandante.setValor(mandante.getValor().add(timeOp.get().getValor()));
				mandante.setTotalEntradas(mandante.getTotalEntradas() + timeOp.get().getTotalEntradas());
			}
		});
		
		timesFora.removeIf(time -> timesCasa.contains(time));
		
		timesCasa.addAll(timesFora);
		
		Map<Long, List<EntradaTimeDTO>> mapMetodos = timesCasa.stream().collect(Collectors.groupingBy(EntradaTimeDTO::getMetodoId));
		
		mapMetodos.forEach((idMetodo, value) -> {
			var metodo = metodos.stream().filter(met -> met.getId().equals(idMetodo)).findFirst().get();
			
			var timesMaisLucrativos = value.stream()
					.filter(entradaTime -> entradaTime.getValor().compareTo(BigDecimal.ZERO) > 0)
					.collect(Collectors.toList());
			
			timesMaisLucrativos.sort(Comparator.comparing(EntradaTimeDTO::getValor).reversed()
			          .thenComparing(EntradaTimeDTO::getNomeTime));
			
			var timesMenosLucrativos = value.stream()
					.filter(entradaTime -> entradaTime.getValor().compareTo(BigDecimal.ZERO) < 0)
					.collect(Collectors.toList());
			
			timesMenosLucrativos.sort(Comparator.comparing(EntradaTimeDTO::getValor)
			          .thenComparing(EntradaTimeDTO::getNomeTime));
			
			metodo.setTimesLucrativos(new EntradaTimeDTOList(
					timesMaisLucrativos.size() < 3 ? timesMaisLucrativos : timesMaisLucrativos.subList(0, 3),
					timesMenosLucrativos.size() < 3 ? timesMenosLucrativos : timesMenosLucrativos.subList(0, 3)));
		});
		
	}

	private Optional<EntradaTimeDTO> mandanteIsListTimesVisitantes(List<EntradaTimeDTO> timesMaisLucrativosFora, EntradaTimeDTO mandante) {
		return timesMaisLucrativosFora.stream()
				.filter(entradaTime -> entradaTime.equals(mandante))
				.findFirst();
	}

	private List<EntradaTimeDTO> findEntradaPorTime(Long bancaId, 
			 List<MetodoPorEntradaDTO> metodos, 
			EstatisticasFilter filter, 
			boolean timeFora, 
			boolean findEntradasMetodosNaoExigemPreenchimentoFlag) {
		
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaTimeDTO.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);

//		Join<Entrada, Time> joinTime = root.join(Entrada_.mandante);
		Path<String> pathTime = root.get(Entrada_.timeMandante);
		Path<Long> pathTimeId = root.get(Entrada_.mandanteIdentificador);
		Predicate flagApostaFavor = cb.equal(root.get(Entrada_.apostaAFavorMandante), Boolean.TRUE);
		
		if(timeFora) {
			pathTime = root.get(Entrada_.timeVisitante);
			pathTimeId = root.get(Entrada_.visitanteIdentificador);
			flagApostaFavor = cb.equal(root.get(Entrada_.apostaAFavorVisitante), Boolean.TRUE);
		} 

		Expression<LocalDate> data = root.get(Entrada_.data);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		predicates.add(joinMetodo.get(Metodo_.id).in(metodos.stream().map(MetodoPorEntradaDTO::getId).collect(Collectors.toList())));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		if(findEntradasMetodosNaoExigemPreenchimentoFlag) {
			predicates.add(cb.or(
					flagApostaFavor, 
					cb.equal(joinMetodo.get(Metodo_.exigirPreenchimentoFlagMandanteVisitante), Boolean.FALSE), 
					joinMetodo.get(Metodo_.exigirPreenchimentoFlagMandanteVisitante).isNull()));
		} else {
			predicates.add(flagApostaFavor);
		}

		query.select(cb.construct(EntradaTimeDTO.class, 
				pathTime,
				pathTimeId,
				joinMetodo.get(Metodo_.id),
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				cb.count(root)));
		
		query.groupBy(pathTime, pathTimeId, joinMetodo)
			.where(predicates.toArray(new Predicate[0]));
		
		return this.manager.createQuery(query).getResultList();
	}
	

	@Override
	public EntradaDadosGeraisDTO findEstatisticasGerais(EstatisticasFilter filter, Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaDadosGeraisDTO.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		var predicates = montarPredicatesComuns(bancaId, filter, cb, joinBanca, data);
		
		query.select(cb.construct(EntradaDadosGeraisDTO.class, 
				cb.count(root),
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				joinBanca.get(Banca_.valor)));
		
		query.where(predicates.toArray(new Predicate[0]));
		
		var dto = this.manager.createQuery(query).getSingleResult();
		
		//Casos que não há entrada ainda
		if(isNull(dto.getValorBanca())) {
			dto.setValorBanca(buscarValorBanca(bancaId));
		}
		
		return dto;
	}

	private BigDecimal buscarValorBanca(Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(BigDecimal.class);
		var root = query.from(Banca.class);
		
		query.select(root.get(Banca_.valor));
		query.where(cb.equal(root.get(Banca_.id), bancaId));
		
		return this.manager.createQuery(query).getSingleResult();
	}

	private LocalDate getFirstDayOfCurrentMonth() {
		return LocalDate.now().withDayOfMonth(1);
	}
	
	private LocalDate getLastDayOfCurrentMonth() {
		var dateNow = LocalDate.now();
		return dateNow.withDayOfMonth(dateNow.getMonth().length(dateNow.isLeapYear()));
	}
	
	/**
	 * 	page=0&size=2
	 *	0x2 = 0
	 *	page=1&size=2
	 *	1x2 = 2
	 */
	private void tratarPaginacao(Pageable pageable, TypedQuery<?> query) {
		int page = pageable.getPageNumber();
		int size = pageable.getPageSize();
		int start = page * size;
		
		query.setFirstResult(start);
		query.setMaxResults(size);
	}
	
	private Long buscarTotalRegistros(Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(Long.class);
		var root = query.from(Entrada.class);

		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		
		var predicates = new LinkedList<Predicate>();
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		predicates.add(cb.equal(root.get(Entrada_.apostaAFavorMandante), Boolean.TRUE));
		
		query.select(cb.count(root))
			.where(predicates.toArray(new Predicate[0]));
		return this.manager.createQuery(query).getSingleResult();
	}
	
	private Long buscarTotalRegistrosMetodosPorEntrada(Long bancaId, EstatisticasFilter filter) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(String.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Expression<LocalDate> data = root.get(Entrada_.data).as(LocalDate.class);
		
		var predicates = new LinkedList<Predicate>();
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		
		query.select(joinMetodo.get(Metodo_.nome)).distinct(true)
			.where(predicates.toArray(new Predicate[0]));
		return (long) this.manager.createQuery(query).getResultList().size();
	}
	
	private List<Predicate> montarPredicatesComuns(Long bancaId, EstatisticasFilter filter, CriteriaBuilder cb, Join<Metodo, Banca> joinBanca, Expression<LocalDate> data) {
		var predicates = new ArrayList<Predicate>();
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		return predicates;
	}

	@Override
	public void updateNomeTime(Time time) {
		updateNomeTimeMandante(time);
		updateNomeTimeVisitante(time);
	}

	private void updateNomeTimeMandante(Time time) {
		CriteriaBuilder cb = this.manager.getCriteriaBuilder();
        CriteriaUpdate<Entrada> update = cb.createCriteriaUpdate(Entrada.class);
        Root<Entrada> root = update.from(Entrada.class);
        update.set("timeMandante", time.getNome());
        update.where(cb.equal(root.get("mandanteIdentificador"), time.getId()));
 
        this.manager.createQuery(update).executeUpdate();
	}
	
	private void updateNomeTimeVisitante(Time time) {
		CriteriaBuilder cb = this.manager.getCriteriaBuilder();
		CriteriaUpdate<Entrada> update = cb.createCriteriaUpdate(Entrada.class);
		Root<Entrada> root = update.from(Entrada.class);
		update.set("timeVisitante", time.getNome());
		update.where(cb.equal(root.get("visitanteIdentificador"), time.getId()));
		this.manager.createQuery(update).executeUpdate();
	}

	@Override
	public Page<EntradaCampeonatoDTO> findCampeonatosLucrativos(EstatisticasFilter filter, Long bancaId, Pageable pageable) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaCampeonatoDTO.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Join<Entrada, Campeonato> joinCampeonato = root.join(Entrada_.campeonato);
		
		Expression<LocalDate> data = root.get(Entrada_.data);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));

		query.select(cb.construct(EntradaCampeonatoDTO.class, 
				joinCampeonato.get(Campeonato_.nome), 
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				cb.count(root)));
		
		query.groupBy(joinCampeonato.get(Campeonato_.nome))
			.orderBy(cb.desc(cb.sum(root.get(Entrada_.lucroPrejuizo))))
			.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<EntradaCampeonatoDTO> typedQuery = this.manager.createQuery(query);
		tratarPaginacao(pageable, typedQuery);
		List<EntradaCampeonatoDTO> dtos = typedQuery.getResultList();
		
		return new PageImpl<>(dtos, pageable, buscarTotalRegistrosCampeonatos(filter, bancaId));
	}

	private long buscarTotalRegistrosCampeonatos(EstatisticasFilter filter, Long bancaId) {
		var cb = this.manager.getCriteriaBuilder();
		var query = cb.createQuery(EntradaCampeonatoDTO.class);
		var root = query.from(Entrada.class);
		
		Join<Entrada, Metodo> joinMetodo = root.join(Entrada_.metodo);
		Join<Metodo, Banca> joinBanca = joinMetodo.join(Metodo_.banca);
		Join<Entrada, Campeonato> joinCampeonato = root.join(Entrada_.campeonato);
		
		Expression<LocalDate> data = root.get(Entrada_.data);
		
		var predicates = new ArrayList<>();
		predicates.add(cb.between(data, filter.getDataInicio(), filter.getDataFim()));
		predicates.add(cb.equal(joinBanca.get(Banca_.id), bancaId));

		query.select(cb.construct(EntradaCampeonatoDTO.class, 
				joinCampeonato.get(Campeonato_.nome), 
				cb.sum(root.get(Entrada_.lucroPrejuizo)),
				cb.count(root)));
		
		query.groupBy(joinCampeonato.get(Campeonato_.nome))
			.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<EntradaCampeonatoDTO> typedQuery = this.manager.createQuery(query);
		return typedQuery.getResultList().size();		
	}

}
