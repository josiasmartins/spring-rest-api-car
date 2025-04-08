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

    // Injeção da requisição HTTP atual para acessar headers
    private final HttpServletRequest request;

    public RoleVerificationAspect(HttpServletRequest request) {
        this.request = request;
    }

    // Executa antes da execução de qualquer método anotado com @VerifyRoles
    @Before("@annotation(VerifyRoles)")
    public void checkRoles(JoinPoint joinPoint) {
        // Obtém o método sendo interceptado
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // Obtém a anotação @VerifyRoles do método
        VerifyRoles verifyRoles = method.getAnnotation(VerifyRoles.class);

        // Converte os valores da anotação para um conjunto de UserRole
        Set<UserRole> allowedRoles = new HashSet<>(Arrays.asList(verifyRoles.value()));

        // Lê o header "roles" da requisição
        String rolesHeader = request.getHeader("roles");

        // Se o header estiver ausente ou vazio, retorna 401 (Unauthorized)
        if (rolesHeader == null || rolesHeader.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing roles header");
        }

        // Converte os valores do header para enums UserRole válidos
        Set<UserRole> userRoles = Arrays.stream(rolesHeader.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(roleStr -> {
                    try {
                        return UserRole.valueOf(roleStr);
                    } catch (IllegalArgumentException e) {
                        return null; // Ignora valores inválidos
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Verifica se o usuário possui pelo menos um dos papéis permitidos
        boolean hasPermission = userRoles.stream().anyMatch(allowedRoles::contains);

        // Se não tiver permissão, lança exceção 401 (Unauthorized)
        if (!hasPermission) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not have required roles");
        }
    }
}
