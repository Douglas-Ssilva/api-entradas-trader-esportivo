package br.com.trader.esportivo.entradas.domain.exception;

public class MetodoNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public MetodoNotFoundException(String message) {
		super(message);
	}

	public MetodoNotFoundException(Long id) {
		this(String.format("Método de id: %d não encontrado.", id));
	}


}
