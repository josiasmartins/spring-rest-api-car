package com.techbuzzblogs.rest.producer.annotations;

import com.techbuzzblogs.rest.producer.enums.UserRole;

import java.lang.annotation.*;

/**
 * Anotação personalizada usada para restringir acesso a métodos ou classes
 * com base nos papéis (roles) definidos.
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // Pode ser usada em métodos ou classes
@Retention(RetentionPolicy.RUNTIME) // Disponível em tempo de execução
@Documented
public @interface VerifyRoles {
    UserRole[] value(); // Lista de papéis permitidos
}
