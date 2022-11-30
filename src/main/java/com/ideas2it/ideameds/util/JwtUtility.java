package com.ideas2it.ideameds.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT utility for token generation
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-26
 */
@Component
public class JwtUtility implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long serialVersionUID = 234234523523L;
    @Value("jwt.secret")
    private String secretKey;

    /**
     * retrieve username from jwt token
     *
     * @param token - send token
     * @return String - get Username from Token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * retrieve expiration date from jwt token
     *
     * @param token - send token
     * @return Date - get Expiration Date From Token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Get a token from jwt token
     *
     * @param token          - send token
     * @param claimsResolver - pass a claims and type(T)
     * @param <T>            - pass token type
     * @return token - get Claim From Token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * for retrieving any information from token we will need the secret key
     *
     * @param token - pass token
     * @return claims - get a claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * check if the token has expired
     *
     * @param token - check token is expired or not
     * @return boolean - is token is expired or not
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * generate token for user
     *
     * @param userDetails - pass a user details
     * @return token - get generate Token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    /**
     * while creating the token -
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     *
     * @param claims  - pass claims of json signature
     * @param subject - pass a subject as string
     * @return String - do Generate Token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * validate token
     *
     * @param token       check validate token
     * @param userDetails - pass user details
     * @return boolean - true or false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
