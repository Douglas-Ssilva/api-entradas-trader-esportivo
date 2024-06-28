package br.com.trader.esportivo.entradas.domain.exception;

public class EntradaNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public EntradaNotFoundException(String message) {
		super(message);
	}

	public EntradaNotFoundException(Long entradaId) {
		this(String.format("Entrada de id: %d não encontrada.", entradaId));
	}

}
