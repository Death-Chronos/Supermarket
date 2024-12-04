package jv.supermarket.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jv.supermarket.security.CustomUserDetailsService;
import jv.supermarket.security.filters.FilterTokenJWT;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    AuthenticationManager authManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Autowired
    FilterTokenJWT filterToken;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String url_produtos = "/supermarket/produto/";
        String url_auth = "/supermarket/auth/";
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**")
                    .permitAll()
                .requestMatchers(url_auth+"**")
                    .permitAll()
                .requestMatchers(HttpMethod.GET, url_produtos+"**")
                    .hasAnyRole("ADMIN", "FUNCIONARIO", "CLIENTE")
                .requestMatchers(HttpMethod.POST, url_produtos + "save")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, url_produtos + "**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, url_produtos + "**")
                    .hasRole("ADMIN"));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.addFilterBefore(filterToken, UsernamePasswordAuthenticationFilter.class);
        
        http.headers(header -> header
                .frameOptions(Customizer.withDefaults()).disable());

        http.csrf(csrf -> csrf.disable());

        http.userDetailsService(userDetailsService());

        return http.build();
    }

}
