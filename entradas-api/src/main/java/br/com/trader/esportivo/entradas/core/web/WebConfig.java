package br.com.trader.esportivo.entradas.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    ShallowEtagHeaderFilter filter() {
		return new ShallowEtagHeaderFilter();
	}

}
