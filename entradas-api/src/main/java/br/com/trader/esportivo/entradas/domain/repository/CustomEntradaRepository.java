package br.com.trader.esportivo.entradas.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.trader.esportivo.entradas.api.model.EntradaTimeDTO;
import br.com.trader.esportivo.entradas.api.model.MetodoPorEntradaDTO;
import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.model.Metodo;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaCampeonatoDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTOConsulta;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTODatas;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDadosGeraisDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaLucroPrejuizoCampeonatosDTO;
import br.com.trader.esportivo.entradas.domain.model.filter.EstatisticasFilter;

public interface CustomEntradaRepository {
	
	Optional<Entrada> findById(Long entradaId, Long bancaId);
	
	List<EntradaDTODatas> buscarEstatisticas(Long bancaId, EstatisticasFilter filter);

	List<Object[]> buscarPercentualSobreBanca(Long bancaId, EstatisticasFilter filter);

	EntradaLucroPrejuizoCampeonatosDTO findLucroPrejuizoCampeonatos(EstatisticasFilter filter, Long bancaId);

	List<EntradaDTOConsulta> findMetodosMaisLucrativos(EstatisticasFilter filter, Long bancaId);
	
	Page<EntradaDTOConsulta> findTimesMaisLucrativosCasa(Long bancaId, EstatisticasFilter filter, Pageable pageable);
	
	Page<EntradaDTOConsulta> findTimesMaisLucrativosFora(Long bancaId, EstatisticasFilter filter, Pageable pageable);
	
	Page<MetodoPorEntradaDTO> findAllMetodosPorEntrada(Long bancaId, EstatisticasFilter filter, Pageable pageable);
	
	EntradaDadosGeraisDTO findEstatisticasGerais(EstatisticasFilter filter, Long bancaId);
	
	void detach(Metodo entrada);
	
	void updateNomeTime(Time time);
	
	Page<EntradaCampeonatoDTO> findCampeonatosLucrativos(EstatisticasFilter filter, Long bancaId, Pageable pageable);
	
}
