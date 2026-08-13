package br.com.giovanni.projetotg.config;

import br.com.giovanni.projetotg.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //Rota de login publica
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        //Rota de cadastro publica
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        //Rotas de busca de mercados e mercado por id publicas
                        .requestMatchers(HttpMethod.GET, "/mercados", "/mercados/{id}").permitAll()
                        //Rotas de busca de produtos, produtos por id e produtos por mercado publicas
                        .requestMatchers(HttpMethod.GET, "/produtos", "/produtos/{id}", "/produtos/mercados/{id}").permitAll()
                        //Rotas de busca de usuários e usuários por id publicas
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/{id}").hasAuthority("ADMIN")
                        //Rota de adicionar mercado somente para role ADMIN
                        .requestMatchers(HttpMethod.POST, "/mercados").hasAuthority("ADMIN")
                        //Rota de editar mercado por id somente para role ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/mercados/{id}").hasAuthority("ADMIN")
                        //Rota de deletar mercado por id somente para role ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/mercados/{id}").hasAuthority("ADMIN")
                        //Rotas restantes acessadas apenas com autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}