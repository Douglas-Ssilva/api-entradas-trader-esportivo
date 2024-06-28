package br.com.trader.esportivo.entradas.domain.exception;

public abstract class EntityNotFoundException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(String message) {
		super(message);
	}

}
