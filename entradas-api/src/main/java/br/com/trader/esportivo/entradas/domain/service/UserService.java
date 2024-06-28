package br.com.trader.esportivo.entradas.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.exception.UserNotFoundException;
import br.com.trader.esportivo.entradas.domain.model.User;
import br.com.trader.esportivo.entradas.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public User findById(Long id) {
		return this.repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public Page<User> findAll(Specification<User> specification, Pageable pageable) {
		return this.repository.findAll(specification, pageable);
	}

	@Transactional
	public User save(User user) {
		try {
			user = this.repository.save(user);
			this.repository.flush();
			return user;
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(String.format("Email já existente : %s", user.getEmail()));
		}
	}

}
