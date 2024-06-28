package br.com.trader.esportivo.entradas.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import br.com.trader.esportivo.entradas.api.assembler.CampeonatoDTOAssembler;
import br.com.trader.esportivo.entradas.api.assembler.TimeDTOAssembler;
import br.com.trader.esportivo.entradas.api.disassembler.CampeonatoInputDTODisassembler;
import br.com.trader.esportivo.entradas.api.helper.WebHelper;
import br.com.trader.esportivo.entradas.api.model.CampeonatoDTO;
import br.com.trader.esportivo.entradas.api.model.TimeDTO;
import br.com.trader.esportivo.entradas.api.model.input.CampeonatoInputDTO;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.CampeonatoSpecs;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.service.CampeonatoService;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {
	
	private static final int TIME_CACHE = 1;

	@Autowired
	private CampeonatoService campeonatoService;
	
	@Autowired
	private CampeonatoDTOAssembler assembler;
	
	@Autowired
	private TimeDTOAssembler assemblerTime;
	
	@Autowired
	private CampeonatoInputDTODisassembler disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Campeonato> pagedResourcesAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Time> pagedResourcesTimeAssembler;
	
	@GetMapping
	public ResponseEntity<PagedModel<CampeonatoDTO>> findAll(@RequestParam(required = false) String nome, @PageableDefault(size = 5) Pageable pageable, ServletWebRequest webRequest) {
		var etag = getEtag(webRequest);
		if (webRequest.checkNotModified(etag)) {
			return null;
		}
		
		var body = pagedResourcesAssembler.toModel(this.campeonatoService.findAll(CampeonatoSpecs.usandoFiltro(nome), pageable), this.assembler);
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(Duration.ofMinutes(TIME_CACHE))) Ao add campeonato não estava aparecendo na listagem
				.eTag(etag)
				.body(body);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CampeonatoDTO> findById(@PathVariable Long id, ServletWebRequest webRequest) {
		var etag = getEtag(webRequest);
		if (webRequest.checkNotModified(etag)) {
			return null;
		}
		
		var body = this.assembler.toModel(this.campeonatoService.findById(id));
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(Duration.ofMinutes(TIME_CACHE)))
				.eTag(etag)
				.body(body);
	}

	@GetMapping("/{campeonatoId}/times")
	public PagedModel<TimeDTO> findAllTeams(@RequestParam(required = false) String nome, @PathVariable Long campeonatoId, @PageableDefault(size = 5) Pageable pageable) {
		Page<Time> timesPage = this.campeonatoService.findAllTimesVinculadosCampeonato(campeonatoId, nome, pageable);
		return this.pagedResourcesTimeAssembler.toModel(timesPage, this.assemblerTime);
	}
	
	@GetMapping("/{campeonatoId}/timesCandidatos")
	public PagedModel<TimeDTO> findAllTeamsCandidatos(@RequestParam(required = false) String nome, @PathVariable Long campeonatoId, @PageableDefault(size = 5) Pageable pageable) {
		Page<Time> timesPage = this.campeonatoService.findAllTimesCandidatos(campeonatoId, nome, pageable);
		return this.pagedResourcesTimeAssembler.toModel(timesPage, this.assemblerTime);
	}
	
	
	@GetMapping("/{campeonatoId}/timesElegiveis")
	public PagedModel<TimeDTO> findTimesPodemParticiparCampeonatoEQueNaoEstaoVinculados(@RequestParam(required = false) String nome, @PathVariable Long campeonatoId, @PageableDefault(size = 5) Pageable pageable) {
		Page<Time> timesPage = this.campeonatoService.findTimesPodemParticiparCampeonatoEQueNaoEstaoVinculados(campeonatoId, nome, pageable);
		return this.pagedResourcesTimeAssembler.toModel(timesPage, this.assemblerTime);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody @Valid CampeonatoInputDTO campeonatoInputDTO) {
		this.campeonatoService.save(this.disassembler.toDomainObject(campeonatoInputDTO));
	}
	
	@PutMapping("/{campeonatoId}")
	public void update(@PathVariable Long campeonatoId, @RequestBody @Valid CampeonatoInputDTO campeonatoInputDTO ) {
		var campeonato = this.campeonatoService.findById(campeonatoId);
		this.disassembler.copyProperties(campeonatoInputDTO, campeonato);
		this.campeonatoService.update(campeonato);
	}
	
	@DeleteMapping("/{campeonatoId}/times/{timeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desvincularTime(@PathVariable Long campeonatoId, @PathVariable Long timeId) {
		this.campeonatoService.desvincularTime(campeonatoId, timeId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{campeonatoId}/times/desvincularVarios")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desvincularTimes(@PathVariable Long campeonatoId, @RequestBody List<Long> idsTime) {
		this.campeonatoService.desvincularTimes(campeonatoId, idsTime);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{campeonatoId}/times/{timeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> vincularTime(@PathVariable Long campeonatoId, @PathVariable Long timeId) {
		this.campeonatoService.vincularTime(campeonatoId, timeId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{campeonatoId}/times/vincularVarios")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> vincularTimes(@PathVariable Long campeonatoId, @RequestBody List<Long> idsTime) {
		this.campeonatoService.vincularTimes(campeonatoId, idsTime);
		return ResponseEntity.noContent().build();
	}
	
	private String getEtag(ServletWebRequest webRequest) {
		var dataAtualizacao = this.campeonatoService.findLastUpdate();
		return WebHelper.getEtag(webRequest, dataAtualizacao);
	}

}
