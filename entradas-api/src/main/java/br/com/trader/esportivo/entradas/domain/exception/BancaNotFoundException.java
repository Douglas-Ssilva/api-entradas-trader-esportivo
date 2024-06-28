package br.com.trader.esportivo.entradas.domain.exception;

public class BancaNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public BancaNotFoundException(String message) {
		super(message);
	}
	
	public BancaNotFoundException(Long id) {
		this(String.format("Banca de id %d não encontrada!", id));
	}


}
