package br.com.trader.esportivo.entradas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.model.Time;

public interface EntradaRepository extends CustomEntradaRepository, JpaRepository<Entrada, Long>, JpaSpecificationExecutor<Entrada>{

}
