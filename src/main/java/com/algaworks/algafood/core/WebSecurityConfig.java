package com.algaworks.algafood.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                    // deixa esse endpoint público
                    .antMatchers("cidades/**").permitAll()
                    // exige que os endpoinst façam a autenticação na requisicao http
                    .anyRequest().authenticated()

                // configura para não trabalhar com cookes que guardam informações para fazer a requisicao sem passar as credenciais
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                    .csrf().disable();

    }
}
