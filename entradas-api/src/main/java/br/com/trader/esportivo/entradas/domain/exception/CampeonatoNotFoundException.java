package br.com.trader.esportivo.entradas.domain.exception;

public class CampeonatoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CampeonatoNotFoundException(String message) {
		super(message);
	}
	
	public CampeonatoNotFoundException(Long id) {
		this(String.format("Campeonato de id: %d não encontrado!", id));
	}
}
