package com.ideas2it.ideameds.security;

import com.ideas2it.ideameds.filter.JwtFilter;
import com.ideas2it.ideameds.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class for security configuration
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-26
 * @version - 1.0
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userService;

    private JwtFilter jwtFilter;

    /**
     * Configuration - Authentication Manager(To Build it which provide Authentication provider )
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * BCryptPassword Encoder to encrypt & decrypt password
     * @return BCryptPasswordEncoder -
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationManager
     * @return AuthenticationManager
     * @throws Exception - occur runtime exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * Configure the http security
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/user","/authenticate")
                .permitAll().antMatchers(HttpMethod.GET,"/user/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/user").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE,"/user").hasRole("CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
