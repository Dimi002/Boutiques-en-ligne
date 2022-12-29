package com.ibrasoft.storeStackProd.rbac.config;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;

import com.ibrasoft.storeStackProd.beans.RolePermission;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.rbac.exceptions.ApiAuthenticationException;
import com.ibrasoft.storeStackProd.rbac.models.SpringUser;
import com.ibrasoft.storeStackProd.repository.RolePermissionRepository;
import com.ibrasoft.storeStackProd.repository.UserRepository;
import com.ibrasoft.storeStackProd.repository.UserRoleRepository;
import com.ibrasoft.storeStackProd.util.ErrorObject;
import com.ibrasoft.storeStackProd.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private UserRoleRepository
            userRoleRepository;
    private RolePermissionRepository rolePermissionRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository,
            UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = null;
        try {
            authentication = getUsernamePasswordAuthentication(request, response);
        } catch (TokenExpiredException e) {
            ErrorObject errorObject = new ErrorObject(HttpStatus.FORBIDDEN.value(), e.getMessage());
            response.setContentType("text/json; charset=UTF-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(Utils.getJsonFromObject(errorObject, ErrorObject.class));
            return;
        } catch (ApiAuthenticationException e) {
            ErrorObject errorObject = new ErrorObject(e.getHttpStatusForResponse().value(), e.getMessage());
            response.setContentType("text/json; charset=UTF-8");
            response.setStatus(e.getHttpStatusForResponse().value());
            response.getWriter().write(Utils.getJsonFromObject(errorObject, ErrorObject.class));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ApiAuthenticationException {
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        if (token != null) {
            String userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token).getSubject();
            if (userName != null) {
                User user = userRepository.findByUsername(userName);
                if (user == null)
                    user = userRepository.findByEmail(userName);
                if (user != null) {
                    SpringUser wrappedUser = new SpringUser(user);
                    if (wrappedUser.isValid()) {
                        List<UserRole> userRoleList = userRoleRepository.findByUserRoleIdUser(user);
                        wrappedUser.getUser().setUsersRolesList(userRoleList);
                        userRoleList.forEach(userRole -> {
                            List<RolePermission> rolePermissionList = rolePermissionRepository
                                    .findByRolePermissionIdRole(userRole.getRole());
                            userRole.getRole().setRolePermissionList(rolePermissionList);
                        });
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName,
                                null, wrappedUser.getAuthorities());
                        return auth;
                    } else {
                        throw new ApiAuthenticationException("The connected user is not valid !", HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return null;
    }
}
