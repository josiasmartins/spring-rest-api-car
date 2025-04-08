package com.techbuzzblogs.rest.producer.annotations;

import com.techbuzzblogs.rest.producer.enums.UserRole;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
public class RoleVerificationAspect {

    private final javax.servlet.http.HttpServletRequest request;

    public RoleVerificationAspect(javax.servlet.http.HttpServletRequest request) {
        this.request = request;
    }

    @Before("@annotation(VerifyRoles)")
    public void checkRoles(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        VerifyRoles verifyRoles = method.getAnnotation(VerifyRoles.class);
        Set<UserRole> allowedRoles = new HashSet<>(Arrays.asList(verifyRoles.value()));

        String rolesHeader = request.getHeader("roles");

        if (rolesHeader == null || rolesHeader.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing roles header");
        }

        Set<UserRole> userRoles = Arrays.stream(rolesHeader.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(roleStr -> {
                    try {
                        return UserRole.valueOf(roleStr);
                    } catch (IllegalArgumentException e) {
                        return null; // Role inválida será ignorada
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        boolean hasPermission = userRoles.stream().anyMatch(allowedRoles::contains);

        if (!hasPermission) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not have required roles");
        }
    }
}
