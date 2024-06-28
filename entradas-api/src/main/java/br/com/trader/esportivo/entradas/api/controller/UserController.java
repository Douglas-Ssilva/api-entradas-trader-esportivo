package br.com.trader.esportivo.entradas.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.trader.esportivo.entradas.api.assembler.UserAssembler;
import br.com.trader.esportivo.entradas.api.disassembler.UserDTODisassembler;
import br.com.trader.esportivo.entradas.api.model.UserDTO;
import br.com.trader.esportivo.entradas.api.model.input.UserInputComSenhaDTO;
import br.com.trader.esportivo.entradas.api.model.input.UserInputDTO;
import br.com.trader.esportivo.entradas.api.model.input.UserInputSemSenhaDTO;
import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.infrastructure.spec.UserSpecs;
import br.com.trader.esportivo.entradas.domain.model.User;
import br.com.trader.esportivo.entradas.domain.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAssembler assembler;
	
	@Autowired
	private UserDTODisassembler disassembler;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping
	public Page<UserDTO> findAll(@RequestParam(required = false) String nome, @PageableDefault(size = 5) Pageable pageable) {
		Page<User> pageUsers = this.userService.findAll(UserSpecs.usandoFiltro(nome), pageable);
		List<UserDTO> usersDTO = this.assembler.toCollectionModel(pageUsers.getContent());
		return new PageImpl<>(usersDTO, pageable, pageUsers.getTotalElements());
	}
	
	@GetMapping("/{userId}")
	public UserDTO findById(@PathVariable Long userId) {
		return this.assembler.toModel(this.userService.findById(userId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO save(@RequestBody @Valid UserInputDTO userInput) {
		userInput.setSenha(this.encoder.encode(userInput.getSenha()));
		return this.assembler.toModel(this.userService.save(this.disassembler.toDomainObject(userInput)));
	}
	
	@PutMapping("/{userId}")
	public UserDTO update(@PathVariable Long userId, @RequestBody @Valid UserInputSemSenhaDTO dto) {
		var user = this.userService.findById(userId);
		this.disassembler.copyProperties(dto, user);
		return this.assembler.toModel(this.userService.save(user));
	}
	
	@PutMapping("/{userId}/senha")
	public UserDTO updateSenha(@PathVariable Long userId, @RequestBody @Valid UserInputComSenhaDTO dto) {
		var user = this.userService.findById(userId);
		if (!this.encoder.matches(dto.getSenhaAntiga(), user.getSenha())) {
			throw new NegocioException("Senha antiga incorreta!");
		}
		user.setSenha(this.encoder.encode(dto.getSenhaNova()));
		return this.assembler.toModel(this.userService.save(user));
	}

}
