package br.com.trader.esportivo.entradas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.trader.esportivo.entradas.domain.model.ContinenteEnum;
import br.com.trader.esportivo.entradas.domain.model.PaisEnum;
import br.com.trader.esportivo.entradas.domain.model.Time;
import br.com.trader.esportivo.entradas.domain.service.CommonService;
import br.com.trader.esportivo.entradas.domain.service.TimeService;


@SpringBootTest
@ActiveProfiles(profiles = "development")
public class CommonTest {
	
	@Autowired
	private TimeService timeService;

	@Test
	void devoConfirmarExistenciaGremio() {
		Optional<String> nomeExists = timeService.entityAlreadyExists(Time.class, 71l, "gremio", PaisEnum.BRASIL, ContinenteEnum.AMERICA);
		assertEquals("Grêmio", nomeExists.get());
	}
	
	@Test
	void devoConfirmarNaoExistenciaGremio() {
		Optional<String> nomeExists = timeService.entityAlreadyExists(Time.class, 71l, "gremio", PaisEnum.HOLANDA, ContinenteEnum.EUROPA);
		assertEquals(false, nomeExists.isPresent());
	}

}
