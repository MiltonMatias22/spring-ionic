package com.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cursomc.security.JWTAuthenticationFilter;
import com.cursomc.security.JWTAuthorizationFilter;
import com.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	//* URLs
	//* DataBase
	private static final String[] PUBLIC_MATCHERS = {
													"/h2-console/**",
												} ;
	//*GETs: read only
	private static final String[] PUBLIC_MATCHERS_GET = {
													"/produtos/**",
													"/categorias/**",
													"/clientes/**"
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
		
		//* Filtro de autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), this.jwtUtil));
		
		//* Filtro de Autorização
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.jwtUtil, this.userDetailsService));
		
		//* Assegura que o sistema não crie sessão de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

		return source;

	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
