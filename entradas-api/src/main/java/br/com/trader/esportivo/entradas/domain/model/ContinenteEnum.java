package br.com.trader.esportivo.entradas.domain.model;

public enum ContinenteEnum {

	ASIA("Ásia"),
	AFRICA("África"),
	EUROPA("Europa"),
	AMERICA("América"),
	OCEANIA("Oceania");
	
	
	private String label;
	
	ContinenteEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
