package br.com.trader.esportivo.entradas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.trader.esportivo.entradas.api.model.MetodoPorEntradaDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaCampeonatoDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTOConsulta;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTODatas;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDadosGeraisDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaLucroPrejuizoCampeonatosDTO;
import br.com.trader.esportivo.entradas.domain.model.filter.EstatisticasFilter;
import br.com.trader.esportivo.entradas.domain.repository.EntradaRepository;

@Service
public class DashboardService {
	
	@Autowired
	private EntradaRepository repository;
	
	public List<EntradaDTODatas> findEstatisticas(Long bancaId, EstatisticasFilter filter) {
		return this.repository.buscarEstatisticas(bancaId, filter);
	}

	public List<Object[]> buscarPercentualSobreBanca(Long bancaId, EstatisticasFilter filter) {
		return this.repository.buscarPercentualSobreBanca(bancaId, filter);
	}

	public EntradaLucroPrejuizoCampeonatosDTO findLucroPrejuizoCampeonatos(EstatisticasFilter filter, Long bancaId) {
		return this.repository.findLucroPrejuizoCampeonatos(filter, bancaId);
	}
	
	public Page<EntradaCampeonatoDTO> findCampeonatosLucrativos(EstatisticasFilter filter, Long bancaId, Pageable pageable) {
		return this.repository.findCampeonatosLucrativos(filter, bancaId, pageable);
	}

	public List<EntradaDTOConsulta> findMetodosMaisLucrativos(EstatisticasFilter filter, Long bancaId) {
		return this.repository.findMetodosMaisLucrativos(filter, bancaId);
	}
	
	public Page<EntradaDTOConsulta> findTimesMaisLucrativosCasa(Long bancaId, Pageable pageable) {
		return this.repository.findTimesMaisLucrativosCasa(bancaId, pageable);
	}
	
	public Page<EntradaDTOConsulta> findTimesMaisLucrativosFora(Long bancaId, Pageable pageable) {
		return this.repository.findTimesMaisLucrativosFora(bancaId, pageable);
	}

	public Page<MetodoPorEntradaDTO> findAllMetodosPorEntrada(Long bancaId , EstatisticasFilter filter, Pageable pageable) {
		return this.repository.findAllMetodosPorEntrada(bancaId, filter, pageable);
	}

	public EntradaDadosGeraisDTO findEstatisticasGerais(EstatisticasFilter filter, Long bancaId) {
		return this.repository.findEstatisticasGerais(filter, bancaId);
	}

}
