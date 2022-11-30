package com.ideas2it.ideameds.filter;

import com.ideas2it.ideameds.service.UserService;
import com.ideas2it.ideameds.util.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT filter
 *
 * @author Parthasarathy elumalai
 * @version 1.0
 * @since 27/11/2022
 */
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtility jwtUtility;

    private UserService userService;

    /**
     * Filter for api
     *
     * @param httpServletRequest  - pass request
     * @param httpServletResponse - pass response
     * @param filterChain         - filter-chain
     * @throws ServletException - occur when servlet page contains exception
     * @throws IOException      - occur when input /output are error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        if ( null != authorization && authorization.startsWith("Bearer ") ) {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
        }

        if ( null != userName && SecurityContextHolder.getContext().getAuthentication() == null ) {
            UserDetails userDetails
                    = userService.loadUserByUsername(userName);

            if ( jwtUtility.validateToken(token, userDetails) ) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
