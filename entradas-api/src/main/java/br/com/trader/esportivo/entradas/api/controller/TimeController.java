package br.com.trader.esportivo.entradas.api.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.trader.esportivo.entradas.api.assembler.TimeDTOAssembler;
import br.com.trader.esportivo.entradas.api.disassembler.TimeInputDTODisassembler;
import br.com.trader.esportivo.entradas.api.helper.WebHelper;
import br.com.trader.esportivo.entradas.api.model.TimeDTO;
import br.com.trader.esportivo.entradas.api.model.input.TimeInputDTO;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.TimeSpecs;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {
	
	private static final int TIME_CACHE = 1;
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private TimeDTOAssembler assembler; 
	
	@Autowired
	private TimeInputDTODisassembler disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Time> pagedResourcesAssembler;
	
	@GetMapping
	public ResponseEntity<PagedModel<TimeDTO>> findAll(@RequestParam(required = false) String nome, @PageableDefault(size = 5) Pageable pageable, ServletWebRequest webRequest) {
		var etag = getEtag(webRequest);
		if (webRequest.checkNotModified(etag)) {
			return null;
		}
		
		var body = this.pagedResourcesAssembler.toModel(this.timeService.findAll(TimeSpecs.usandoFiltro(nome), pageable), this.assembler);
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(Duration.ofMinutes(TIME_CACHE)))
				.eTag(etag)
				.body(body);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<TimeDTO> findById(@PathVariable Long id, ServletWebRequest webRequest) {
		var etag = getEtag(webRequest);
		if (webRequest.checkNotModified(etag)) {
			return null;
		}
		
		var time = this.assembler.toModel(this.timeService.findById(id));
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(Duration.ofMinutes(TIME_CACHE)))
				.eTag(etag)
				.body(time);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody @Valid TimeInputDTO timeDTO) {
		this.timeService.save(this.disassembler.toDomainObject(timeDTO));
	}
	
	@PutMapping("/{timeId}")
	public TimeDTO update(@PathVariable Long timeId, @RequestBody @Valid TimeInputDTO timeDTO) {
		var time = this.timeService.findById(timeId);
		var temAlteracaoNome = !StringUtils.equals(time.getNome(), timeDTO.getNome());
		this.disassembler.copyProperties(timeDTO, time);
		return this.assembler.toModel(this.timeService.update(time, temAlteracaoNome));
	}
	
	@DeleteMapping("/{timeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long timeId) {
		this.timeService.delete(timeId);
	}
	

	private String getEtag(ServletWebRequest webRequest) {
		var dataAtualizacao = this.timeService.findLastUpdate();
		return WebHelper.getEtag(webRequest, dataAtualizacao);
	}
}
