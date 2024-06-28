package br.com.trader.esportivo.entradas.api.controller;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.trader.esportivo.entradas.api.assembler.MetodoDTOAssembler;
import br.com.trader.esportivo.entradas.api.disassembler.MetodoDTODisassembler;
import br.com.trader.esportivo.entradas.api.model.MetodoDTO;
import br.com.trader.esportivo.entradas.api.model.input.MetodoInputDTO;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.MetodoSpecs;
import br.com.trader.esportivo.entradas.domain.model.Metodo;
import br.com.trader.esportivo.entradas.domain.service.MetodoService;

@RestController
@RequestMapping("/{bancaId}/metodos")
public class MetodoController {
	
	@Autowired
	private MetodoService service;
	
	@Autowired
	private MetodoDTOAssembler assembler;
	
	@Autowired
	private MetodoDTODisassembler disassembler;
	
	@GetMapping
	public Page<MetodoDTO> findAll(@PathVariable Long bancaId, @RequestParam(required = false) String nome, @PageableDefault(size = 5) Pageable pageable) {
		Page<Metodo> pageMetodos = this.service.findAll(MetodoSpecs.usandoFiltro(bancaId, nome), pageable);
		List<MetodoDTO> metodosDTO = this.assembler.toCollectionModel(pageMetodos.getContent());
		return new PageImpl<>(metodosDTO, pageable, pageMetodos.getTotalElements());
	}
	
	@GetMapping("/{metodoId}")
	public MetodoDTO findById(@PathVariable Long bancaId, @PathVariable Long metodoId) {
		return this.assembler.toModel(this.service.findById(metodoId, bancaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MetodoDTO save(@PathVariable Long bancaId, @RequestBody @Valid MetodoInputDTO dto) {
		var metodo = this.disassembler.toDomainObject(dto);
		return this.assembler.toModel(this.service.save(bancaId, metodo));
	}
	
	@PutMapping("/{metodoId}")
	public MetodoDTO update(@PathVariable Long bancaId, @PathVariable Long metodoId, @RequestBody @Valid MetodoInputDTO dto) {
		var metodo = this.service.findById(metodoId, bancaId);
		this.disassembler.copyProperties(dto, metodo);
		return this.assembler.toModel(this.service.save(bancaId, metodo));
	}
	
	@DeleteMapping("/{metodoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long bancaId, @PathVariable Long metodoId) {
		var metodo = this.service.findById(metodoId, bancaId);
		this.service.delete(metodo);
	}

}
