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
     * Get the username from the token.
     *
     * @param token The JWT token - send token
     * @return The username - get Username from Token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Get the expiration date from the token.
     *
     * @param token The JWT token
     * @return The expiration date of the token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Given a token, get all the claims from it, and then apply the claimsResolver function to the claims.
     * <p>
     * The claimsResolver function is a lambda expression that takes a Claims object and returns a generic type T
     *
     * @param token          The JWT token
     * @param claimsResolver A function that takes in a Claims object and returns a value of type T.
     * @return A generic type T is being returned.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * It takes a token and returns the claims
     *
     * @param token The token that needs to be validated.
     * @return The claims are being returned.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * If the expiration date of the token is before the current date, then the token is expired
     *
     * @param token The check JWT token is expired or not
     * @return A boolean value - is token is expired or not.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * The function takes a userDetails object and returns a JWT token
     *
     * @param userDetails This is the user object that contains the user's information.
     * @return A JWT token - pass a token as String.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * It takes a map of claims, a subject, and a secret key, and returns a JWT token
     *
     * @param claims  A map of claims that will be added to the JWT.
     * @param subject The subject of the token.
     * @return A JWT token - generate token with signature
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * If the username in the token matches the username in the userDetails object, and the token is not expired, then
     * return true
     *
     * @param token       The JWT token to validate
     * @param userDetails The user details object that contains the username and password of the user.
     * @return A boolean value if user exit - true
     * if not - false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
