package org.sergio.reactive_product_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() throws Exception {

        List<UserDetails> userdetails = List.of(
                User.withUsername("user1")
                        .password("{noop} user1") // {noop} <- indicates to Spring that the password is not encrypted
                        .roles("USERS")
                        .build(),
                User.withUsername("admin")
                        .password("{noop} admin") // {noop} <- indicates to Spring that the password is not encrypted
                        .roles("USERS", "ADMINS")
                        .build(),
                User.withUsername("operator")
                        .password("{noop} operator") // {noop} <- indicates to Spring that the password is not encrypted
                        .roles("OPERATORS")
                        .build());

        return new MapReactiveUserDetailsService(userdetails);
    }

    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
        serverHttpSecurity.csrf(c -> c.disable())
        .authorizeExchange(auth -> {
            auth.pathMatchers(HttpMethod.POST, "/path").hasAnyRole("ADMIN")
        })
        
        return serverHttpSecurity.build( );
    }
}
