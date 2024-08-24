package org.example.moviereservationsystem.authentication;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.example.moviereservationsystem.user.UserEntityDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;




@Component
public class JWTtoUserConvertor implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        UserEntityDetails user = new UserEntityDetails();
        user.setUsername(source.getSubject());
        return new UsernamePasswordAuthenticationToken(user, source, extractAuthorities(source));
    }
    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<String> roles = jwt.getClaim("roles");

        if (roles == null) {
            roles = Collections.emptyList();
        }
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

