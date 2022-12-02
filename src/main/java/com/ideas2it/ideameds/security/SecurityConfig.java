package com.ideas2it.ideameds.security;

import com.ideas2it.ideameds.filter.JwtFilter;
import com.ideas2it.ideameds.service.UserServiceImpl;
import lombok.AllArgsConstructor;
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
 * @version - 1.0
 * @since - 2022-11-26
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

    private UserServiceImpl userService;

    private JwtFilter jwtFilter;

    /**
     * Configuration - Authentication Manager(To Build it which provide Authentication provider )
     *
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception - occur runtime exception
     */
/*    @Override*/
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * BCryptPassword Encoder to encrypt and decrypt password
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationManager
     *
     * @return AuthenticationManager
     * @throws Exception - occur runtime exception
     */
/*    @Override*/
/*    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }*/

    /**
     * Configure the http security
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception - occur runtime exception
     */
 /*   @Override*/
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/user", "/authenticate")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/user/user-medicine/{id}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/user/order/{id}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/user/{id}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/user").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.POST, "/prescription/{userId}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/prescription/{prescriptionId}").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/prescription/user/{userId}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/addToCart/{userId}/{prescriptionId}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/prescription/{userId}/{prescriptionId}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.POST, "/brand").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/brand/byName").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/brand/getAll").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/brand/{brandId}").hasRole("ADMIN")
                .antMatchers("/brand/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/medicine/getAll").hasRole("CUSTOMER")
                .antMatchers("/medicine", "/medicine/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/medicine").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/medicine/{medicineName}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/brandItems/getAll").hasRole("CUSTOMER")
                .antMatchers("/brandItems/**", "/medicineName/{medicineName}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/brandItems/{brandItemsName}", "/medicineName/{medicineName}").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/brandItems").hasRole("CUSTOMER")
                .antMatchers("/warehouse", "/warehouse/**").hasRole("ADMIN")
                .antMatchers("/cart").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT,"/order").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/order", "/allorder").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
