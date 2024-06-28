package br.com.trader.esportivo.entradas.api.helper;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

public class WebHelper {
	
	private static final String VALUE_DEFAULT_EMPTY_TABLE = "0";
	
	public static String getEtag(ServletWebRequest webRequest, Optional<OffsetDateTime> dataAtualizacao) {
		ShallowEtagHeaderFilter.disableContentCaching(webRequest.getRequest());
		var etag = VALUE_DEFAULT_EMPTY_TABLE;

		if (dataAtualizacao.isPresent()) {
			etag = String.valueOf(dataAtualizacao.get().toEpochSecond());
		}
		return etag;
	}
	

}
