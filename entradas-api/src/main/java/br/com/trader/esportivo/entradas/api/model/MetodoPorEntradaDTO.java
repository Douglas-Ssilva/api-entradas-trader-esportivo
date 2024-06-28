package br.com.trader.esportivo.entradas.api.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import br.com.trader.esportivo.entradas.api.helper.EntradaUtils;
import br.com.trader.esportivo.entradas.domain.model.dto.EntradaDTODatas;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetodoPorEntradaDTO {
	
	private Long id;

	private String nome;
	
	private Long totalEntradas;
	
	private BigDecimal lucroPrejuizo;
	
	private BigDecimal porcentagemStake;
	
	private Long golsFavor;

	private Long golsContra;
	
	private Long redCard;
	
	private BigDecimal correcao;
	
	private BigDecimal totalGolsFavor;	

	private BigDecimal totalGolsContra;	
	
	private Boolean exigirPreenchimentoFlagRedCard;
	
	private Boolean exigirPreenchimentoFlagGolContraFavor;
	
	private EntradaTimeDTOList timesLucrativos;	
	
	private List<EntradaDTODatas> dadosGrafico;

	public MetodoPorEntradaDTO(Long id, String nome, Boolean exigirPreenchimentoFlagRedCard, 
			Boolean exigirPreenchimentoFlagGolContraFavor, Long totalEntradas, BigDecimal lucroPrejuizo, BigDecimal stakePadrao) {
		super();
		this.id = id;
		this.nome = nome;
		this.exigirPreenchimentoFlagRedCard = exigirPreenchimentoFlagRedCard;
		this.exigirPreenchimentoFlagGolContraFavor = exigirPreenchimentoFlagGolContraFavor;
		this.totalEntradas = totalEntradas;
		this.lucroPrejuizo = lucroPrejuizo;
		this.porcentagemStake = EntradaUtils.getPercent(lucroPrejuizo, stakePadrao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetodoPorEntradaDTO other = (MetodoPorEntradaDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
