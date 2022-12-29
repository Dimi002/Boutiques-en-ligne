package com.ibrasoft.storeStackProd.rbac.config;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.rbac.exceptions.ApiAuthenticationException;
import com.ibrasoft.storeStackProd.rbac.models.LoginCredentials;
import com.ibrasoft.storeStackProd.rbac.models.SpringUser;
import com.ibrasoft.storeStackProd.rbac.models.UserWrapper;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.util.ErrorObject;
import com.ibrasoft.storeStackProd.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private SpecialistService specialistService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
            SpecialistService specialistService) {
        this.authenticationManager = authenticationManager;
        this.specialistService = specialistService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LoginCredentials credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginCredentials.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.getLogin(), credentials.getPassword(), new ArrayList<>());
            Authentication auth = authenticationManager.authenticate(authenticationToken);
            return auth;
        } catch (IOException e) {
            throw new ApiAuthenticationException("Cannot get credentials from the request.", e, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SpringUser wrapperUser = (SpringUser) authResult.getPrincipal();
        if (wrapperUser.isValid()) {
            User connectedUser = wrapperUser.getUser();
            connectedUser.setUsersRolesList(null);
            Specialist specialist = this.specialistService.getSpecialistBeforeAuth(connectedUser.getId());
            if (specialist != null) {
                if (specialist.getSpecialistSpecialitiesList() != null) {
                    specialist.setSpecialitiesList(specialist.getSpecialistSpecialitiesList());
                }
                specialist.setAppointmentsList(null);
                specialist.setSpecialistSpecialityList(null);
                specialist.setUserId(null);
                specialist.setPlanings(null);
                connectedUser.setSpecialist(specialist);
            }

            Date expiresAt = new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME);
            String token = JWT.create().withSubject(wrapperUser.getUsername()).withExpiresAt(expiresAt)
                    .sign(HMAC512(JwtProperties.SECRET.getBytes()));
            UserWrapper wrapper = new UserWrapper();
            wrapper.setUser(connectedUser);
            wrapper.setToken(token);
            wrapper.setTokenExpiresAt(expiresAt);
            response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
            response.setContentType("text/json; charset=UTF-8");
            response.getWriter().write(Utils.getJsonFromObject(wrapper, UserWrapper.class));
            return;
        } else {
            ErrorObject errorObject = new ErrorObject(HttpStatus.FORBIDDEN.value(),
                    "Cannot use this account because it is invalid.");
            response.setContentType("text/json; charset=UTF-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(Utils.getJsonFromObject(errorObject, ErrorObject.class));
            return;
        }
    }
}
