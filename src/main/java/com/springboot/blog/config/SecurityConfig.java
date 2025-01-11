package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeHttpRequests((authorize) ->
                //Before This line is comment we have give the permit perform all operation for the all user.
                //authorize.anyRequest().authenticated()

                //now we are giving permit by specifc uaser for specific operations
                authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated()

        ).httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
    /* Below i used In-Memory  Authentication thats why we define below userDetailsService() */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//                .build();
//        UserDetails amar = User.builder().username("amar")
//                .password(passwordEncoder().encode("amar"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, amar);
//    }
}
