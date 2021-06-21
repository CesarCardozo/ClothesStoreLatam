package com.experimentality.ClothesstoreLATAM.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ResourceServerConfig {

	/**
	 * Método que genera un Bean que permite soporte para CORS desde el origen de la aplicación angular
	 * 
	 * La configuración expuesta abajo especifica como origen la URL de la aplicación angular, permite los métodos
	 * HTTP comunes (GET, POST, PUT, DELETE) y OPTIONS (debido a que OAuth2, para autenticación a veces lo requiere),
	 * los headers para el tipo de contenido y para autorización
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(
				"http://localhost:4200")
				);
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
		
		//Aplica la configuración de CORS para todas las rutas
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	/**
	 * Método que crea un Bean que tiene la precedencia más alta al momento del manejo de peticiones para que no tenga
	 * conflicto con otros filtros
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> cors(){
		FilterRegistrationBean<CorsFilter> corsFilterBean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		corsFilterBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return corsFilterBean;
	}

}
