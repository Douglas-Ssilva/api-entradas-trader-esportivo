package br.com.trader.esportivo.entradas.api.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.trader.esportivo.entradas.api.assembler.EntradaDTOAssembler;
import br.com.trader.esportivo.entradas.api.disassembler.EntradaDTODisassembler;
import br.com.trader.esportivo.entradas.api.model.EntradaDTO;
import br.com.trader.esportivo.entradas.api.model.input.EntradaInputDTO;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.EntradaSpecs;
import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.service.EntradaService;

@RestController
@RequestMapping("/{bancaId}/entradas")
public class EntradaController {

	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private EntradaDTODisassembler disassembler;
	
	@Autowired
	private EntradaDTOAssembler assembler;
	
	
	@GetMapping
	public Page<EntradaDTO> findAll(@PathVariable Long bancaId, @PageableDefault(size = 5) Pageable pageable) {
		Page<Entrada> pageEntradas = this.entradaService.findAll(EntradaSpecs.usandoFiltro(bancaId), pageable);
		List<EntradaDTO> metodosDTO = this.assembler.toCollectionModel(pageEntradas.getContent());
		return new PageImpl<>(metodosDTO, pageable, pageEntradas.getTotalElements());
	}
	
	@GetMapping("/{entradaId}")
	public EntradaDTO findById(@PathVariable Long entradaId, @PathVariable Long bancaId) {
		return this.assembler.toModel(this.entradaService.findById(entradaId, bancaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@PathVariable Long bancaId, @RequestBody @Valid List<EntradaInputDTO> dtos) {
		this.entradaService.save(this.disassembler.toDomainObject(dtos), bancaId);
	}
	
	@PutMapping("/{entradaId}")
	public void update(@PathVariable Long bancaId, @PathVariable Long entradaId, @RequestBody @Valid EntradaInputDTO dto) {
		var entrada = this.entradaService.findById(entradaId, bancaId);
		this.disassembler.copyProperties(dto, entrada);
		this.entradaService.save(Arrays.asList(entrada), bancaId);
	}
	
	@DeleteMapping("/{entradaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long entradaId, @PathVariable Long bancaId) {
		this.entradaService.delete(entradaId, bancaId);
	}
}
















