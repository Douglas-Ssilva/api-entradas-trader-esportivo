package br.com.trader.esportivo.entradas.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.UserDTO;
import br.com.trader.esportivo.entradas.domain.model.User;

@Component
public class UserAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserDTO toModel(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	
	public List<UserDTO> toCollectionModel(List<User> users) {
		return users.stream()
				.map(u -> toModel(u))
				.collect(Collectors.toList());
	}
	
}
