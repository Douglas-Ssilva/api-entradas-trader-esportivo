package br.com.trader.esportivo.entradas.api.controller;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trader.esportivo.entradas.api.helper.EntradaUtils;
import br.com.trader.esportivo.entradas.api.model.MetodoPorEntradaDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaCampeonatoDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTOConsulta;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTOEstatistica;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDadosGeraisDTO;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaLucroPrejuizoCampeonatosDTO;
import br.com.trader.esportivo.entradas.domain.model.filter.EstatisticasFilter;
import br.com.trader.esportivo.entradas.domain.service.DashboardService;

@RestController
@RequestMapping("/{bancaId}/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/total-entradas-metodos")
	public Page<MetodoPorEntradaDTO> findAllMetodosPorEntrada(@PathVariable Long bancaId, EstatisticasFilter filter, @PageableDefault(size = 5) Pageable pageable) {
		return this.dashboardService.findAllMetodosPorEntrada(bancaId , filter, pageable);
	}
	
	@GetMapping("/dados-gerais")
	public EntradaDadosGeraisDTO findEstatisticasGerais(@PathVariable Long bancaId, EstatisticasFilter filter) {
		return this.dashboardService.findEstatisticasGerais(filter, bancaId);
	}
	
	@GetMapping("/estatisticas")
	public EntradaDTOEstatistica findEstatisticasMesCorrente(@PathVariable Long bancaId, EstatisticasFilter filter){
		var entradas = this.dashboardService.findEstatisticas(bancaId, filter);
		var percentualEValorBanca = this.dashboardService.buscarPercentualSobreBanca(bancaId, filter);
		var porcentagem = BigDecimal.ZERO;
		if (isNotEmpty(percentualEValorBanca)) {
			var lucroPrejuizo = (BigDecimal) percentualEValorBanca.get(0)[0];
			var valorBanca = (BigDecimal) percentualEValorBanca.get(0)[1];
			porcentagem = EntradaUtils.getPercent(lucroPrejuizo, valorBanca);
		}
		return new EntradaDTOEstatistica(entradas, porcentagem);
	}
	
//	@GetMapping("/lucro-prejuizo-campeonatos")
//	public EntradaLucroPrejuizoCampeonatosDTO findLucroPrejuizoCampeonatos(@PathVariable Long bancaId, EstatisticasFilter filter) {
//		return this.dashboardService.findLucroPrejuizoCampeonatos(filter, bancaId);
//	}
	
	@GetMapping("/lucro-prejuizo-campeonatos")
	public Page<EntradaCampeonatoDTO> findLucroPrejuizoCampeonatos(@PathVariable Long bancaId, EstatisticasFilter filter, @PageableDefault(size = 5) Pageable pageable) {
		return this.dashboardService.findCampeonatosLucrativos(filter, bancaId, pageable);
	}
	
	@GetMapping("/metodos-mais-lucrativos")
	public List<EntradaDTOConsulta> findMetodosMaisLucrativos(@PathVariable Long bancaId, EstatisticasFilter filter) {
		return this.dashboardService.findMetodosMaisLucrativos(filter, bancaId);
	}
	
	@GetMapping("/times-mais-lucrativos-casa")
	public Page<EntradaDTOConsulta> findTimesMaisLucrativosCasa(@PathVariable Long bancaId, EstatisticasFilter filter, @PageableDefault(size = 5) Pageable pageable) {
		return this.dashboardService.findTimesMaisLucrativosCasa(bancaId, filter, pageable);
	}
	
	@GetMapping("/times-mais-lucrativos-fora")
	public Page<EntradaDTOConsulta> findTimesMaisLucrativosFora(@PathVariable Long bancaId, EstatisticasFilter filter, @PageableDefault(size = 5) Pageable pageable) {
		return this.dashboardService.findTimesMaisLucrativosFora(bancaId, filter, pageable);
	}

	
}
