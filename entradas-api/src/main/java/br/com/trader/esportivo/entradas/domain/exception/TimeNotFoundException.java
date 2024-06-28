package br.com.trader.esportivo.entradas.domain.exception;

public class TimeNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public TimeNotFoundException(String message) {
		super(message);
	}
	
	public TimeNotFoundException(Long id) {
		this(String.format("Time de id: %d não encontrado!", id));
	}
}
