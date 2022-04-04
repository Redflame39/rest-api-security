package com.epam.esm.configuration;

import com.epam.esm.controller.filter.JwtAccessFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAccessFilter jwtAccessFilter;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/login", "/signin").permitAll()
                    .antMatchers(HttpMethod.GET, "/certificates").permitAll()
                    .antMatchers(HttpMethod.GET, "/users/**", "/tags", "/users/**/orders")
                        .hasAnyRole("USER", "ADMINISTRATOR")
                    .antMatchers(HttpMethod.POST, "/users/**/orders").hasAnyRole("USER", "ADMINISTRATOR")
                    .antMatchers(HttpMethod.POST, "/certificates", "/tags").hasRole("ADMINISTRATOR")
                    .antMatchers(HttpMethod.PUT, "/certificates", "/tags").hasRole("ADMINISTRATOR")
                    .antMatchers(HttpMethod.DELETE, "/certificates", "/tags").hasRole("ADMINISTRATOR")
                    .antMatchers(HttpMethod.PATCH, "/certificates").hasRole("ADMINISTRATOR")
                    .anyRequest().authenticated()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtAccessFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
