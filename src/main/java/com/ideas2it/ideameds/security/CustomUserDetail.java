package com.ideas2it.ideameds.security;

import com.ideas2it.ideameds.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Class for user details
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-26
 */
@Slf4j
public class CustomUserDetail implements UserDetails {

    private User user;

    /**
     * Initialize the user object
     * @param user to initialize required user
     */
    public CustomUserDetail(User user) {
        this.user = user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRoleType().toString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get the email ID of the user
     * @return returns email ID
     */
    @Override
    public String getUsername() {
        return user.getEmailId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return !user.isDeletedStatus();
    }
}
