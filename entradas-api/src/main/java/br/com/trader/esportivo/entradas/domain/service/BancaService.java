package br.com.trader.esportivo.entradas.domain.service;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.trader.esportivo.entradas.domain.exception.BancaNotFoundException;
import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.SaqueAporte;
import br.com.trader.esportivo.entradas.domain.repository.BancaRepository;

@Service
public class BancaService {
	
	@Autowired
	private BancaRepository repository;
	
	@Autowired
	private MetodoService metodoService;
	
	
	public Page<Banca> findAll(Specification<Banca> spec, Pageable pageable) {
		return this.repository.findAll(spec, pageable);
	}

	public Banca findById(Long userId, Long bancaId) {
		return this.repository.findByIdAndUserId(bancaId, userId).orElseThrow(() -> new BancaNotFoundException(bancaId));
	}
	
	public Banca findById(Long bancaId) {
		return this.repository.findById(bancaId).orElseThrow(() -> new BancaNotFoundException(bancaId));
	}

	public Banca findByBancaPrincipal(Long userId) {
		return this.repository.findByPrincipalTrueAndUserId(userId);
	}
	
	public Banca findByIdDetail(Long userId, Long bancaId) {
		var banca = this.repository.findByIdFetchValores(bancaId).orElseThrow(() -> new BancaNotFoundException(bancaId));
		
		banca.setTotalAporte(banca.getSaqueAporte().stream()
				.map(SaqueAporte::getSaqueOuAporte)
				.filter(valor -> valor.compareTo(BigDecimal.ZERO) > 0)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
		banca.setTotalSaque(banca.getSaqueAporte().stream()
				.map(SaqueAporte::getSaqueOuAporte)
				.filter(valor -> valor.compareTo(BigDecimal.ZERO) < 0)
				.map(BigDecimal::abs)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
		return banca;
	}

	public Banca save(Long userId, Banca banca) {
		verificarFlagBancaPrincipal(userId, banca);
		sacarOuAportar(userId, banca);
		return this.save(banca);
	}

	@Transactional
	public Banca save(Banca banca) {
		return this.repository.save(banca);
	}

	public void delete(Long userId, Long bancaId) {
		var banca = findById(userId, bancaId);
		this.metodoService.delete(banca);
		this.repository.delete(banca);
	}

	private void sacarOuAportar(Long userId, Banca banca) {
		if (nonNull(banca.getId())) {
			sacar(banca);
			aportar(banca);
		}
	}
	
	private void aportar(Banca banca) {
		if (nonNull(banca.getAporte())) {
			banca.aportar(banca.getAporte());
		}
	}

	private void sacar(Banca banca) {
		if (nonNull(banca.getSaque())) {
			if (banca.getSaque().compareTo(banca.getValor()) > 0) {
				throw new NegocioException(String.format("Valor do saque: %.2f maior que valor da banca: %.2f.", banca.getSaque(), banca.getValor()));
			}
			banca.sacar(banca.getSaque());
		}
	}
	
	private void verificarFlagBancaPrincipal(Long userId, Banca banca) {
		if(banca.flagPrincipalIsSelecionada()) { 
			var existsBancaComFlagPrincipalSelecionada = repository.existeBancaPrincipalSalva(userId, banca.getId());
			if(existsBancaComFlagPrincipalSelecionada) {
				throw new NegocioException("Já existe uma banca com a flag principal selecionada.");
			}
		}
	}

}
