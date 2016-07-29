package com.langying.controller.security;

import com.langying.exception.ApiException;
import com.langying.models.UUser;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

/**
 * Created by zb on 2015/6/30.
 */
@CompileStatic
@TypeChecked
public class AuthenticationProviderCustom implements AuthenticationProvider{

    private UserSecurityService securityService;

    public AuthenticationProviderCustom(UserSecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        if(token.getPrincipal() instanceof UUser)
            return token;
        UUser opUser = null;
        try {
            opUser = securityService.loadUserByUsername(token.getName(),token.getCredentials().toString());
        } catch (ApiException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage(),e);
        }

        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        return new UsernamePasswordAuthenticationToken(opUser, opUser.getUserPwd(),authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
