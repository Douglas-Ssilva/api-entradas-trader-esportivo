package br.com.trader.esportivo.entradas.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Long id) {
		this(String.format("Usuário de id: %d não encontrado!", id));
	}

}
