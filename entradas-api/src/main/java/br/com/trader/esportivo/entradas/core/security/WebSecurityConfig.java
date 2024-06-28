package br.com.trader.esportivo.entradas.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}

   @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                	.anyRequest().permitAll()
                
                .and()
                	.sessionManagement()
                		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Removendo cookies que estavam sendo enviados mantendo estado da aplicação
        
                .and()
                	.csrf()
                		.disable();//Falsificação da request com o cookie. Foi preciso desabilitar para conseguirmos fazer o post. Como nossa aplicação não usará mais cookies, passaremos as credenciais a cada request, podemos desabilitar
        
        return http.build();
    }
	   
}
