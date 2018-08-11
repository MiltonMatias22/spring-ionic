package com.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private Environment env;
	
	//* URLs
	//* DataBase
	private static final String[] PUBLIC_MATCHERS = {
													"/h2-console/**",
												} ;
	//*GETs: read only
	private static final String[] PUBLIC_MATCHERS_GET = {
													"/produtos/**",
													"/categorias/**"
												};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//* liberando acesso ao h2-console
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
		
		http.cors().and()
			  .csrf().disable(); //* Proteção a CSRF desabilitada
		
		http.authorizeRequests()
					.antMatchers(PUBLIC_MATCHERS).permitAll()
					.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
					.anyRequest()
					.authenticated();
		
		//* Assegura que o sistema não crie sessão de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

		return source;

	}
}
