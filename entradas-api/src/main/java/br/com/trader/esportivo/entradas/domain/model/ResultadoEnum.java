package br.com.trader.esportivo.entradas.domain.model;


/**
 * Superfavorito
 * - Uma equipe superfavorita quando joga dentro de seus domínios possui uma faixa de odds entre 1.01 até 1.39. Enquanto fora de casa essa faixa de odds muda para 1.01 até 1.50.

 *	Favorito
   - Jogando em seu estádio, eu determino a faixa de odds de 1.40 até 2.20 para considerar uma equipe como favorita.J ogando como visitante considero uma odd de 1.50 até 2.20. 
 
 *	Não favorito
   - Eu considero uma equipe não-favorita na faixa de odds de 4.0 até 7.5 
  
 * Parelho
   - A faixa de odds dessas equipes está acima de 2.20 até 3.98.

 * Zebra
   - Uma zebra é aquela equipe que está com a faixa de odds acima de um não-favorito. Isto é, a partir de 8 até 1000.
 */

public enum ResultadoEnum {

	EMPATE, 
	SUPERFAVORITO_VENCENDO,
	FAVORITO_VENCENDO,
	NAO_FAVORITO_VENCENDO,
	PARELHO_VENCENDO,
	ZEBRA_VENCENDO;
	
}
