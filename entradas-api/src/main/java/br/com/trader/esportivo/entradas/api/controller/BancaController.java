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

import br.com.trader.esportivo.entradas.api.assembler.BancaDTOAssembler;
import br.com.trader.esportivo.entradas.api.disassembler.BancaDTODisassembler;
import br.com.trader.esportivo.entradas.api.model.BancaDTO;
import br.com.trader.esportivo.entradas.api.model.input.BancaInputDTO;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.BancasSpecs;
import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.service.BancaService;
import br.com.trader.esportivo.entradas.domain.service.UserService;


@RestController
@RequestMapping("/{userId}/bancas")
public class BancaController {
	
	@Autowired
	private BancaService bancaService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BancaDTOAssembler assembler;
	
	@Autowired
	private BancaDTODisassembler disassembler;
	
	@GetMapping
	public Page<BancaDTO> findAll(@RequestParam(required = false) String nome, @PathVariable Long userId, @PageableDefault(size = 5) Pageable pageable) {
		var user = this.userService.findById(userId);
		Page<Banca> pageBancas = this.bancaService.findAll(BancasSpecs.usandoFiltro(user, nome), pageable);
		List<BancaDTO> bancasDTO = this.assembler.toCollectionModel(pageBancas.getContent());
		return new PageImpl<>(bancasDTO, pageable, pageBancas.getTotalElements());
	}
	
	@GetMapping("/{bancaId}")
	public BancaDTO findById(@PathVariable Long userId, @PathVariable Long bancaId) {
		return this.assembler.toModel(this.bancaService.findById(userId, bancaId));
	}
	
	@GetMapping("/{bancaId}/detail")
	public BancaDTO findByIdDetail(@PathVariable Long userId, @PathVariable Long bancaId) {
		return this.assembler.toModel(this.bancaService.findByIdDetail(userId, bancaId));
	}
	
	@GetMapping("/{bancaId}/principal")
	public BancaDTO findByBancaPrincipal(@PathVariable Long userId) {
		return this.assembler.toModel(this.bancaService.findByBancaPrincipal(userId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BancaDTO save(@PathVariable Long userId, @RequestBody @Valid BancaInputDTO dto) {
		var user = this.userService.findById(userId);
		var banca = this.disassembler.toDomainObject(dto);
		banca.setUser(user);
		return this.assembler.toModel(this.bancaService.save(userId, banca));
	}
	
	@PutMapping("/{bancaId}")
	public BancaDTO update(@PathVariable Long userId, @PathVariable Long bancaId, @RequestBody @Valid BancaInputDTO dto) {
		var banca = this.bancaService.findById(userId, bancaId);
		this.disassembler.copyProperties(dto, banca);
		return this.assembler.toModel(this.bancaService.save(userId, banca));
	}
	
	@DeleteMapping("/{bancaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long userId, @PathVariable Long bancaId) {
		this.bancaService.delete(userId, bancaId);
	}
	
}
