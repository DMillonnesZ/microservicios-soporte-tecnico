package com.tecsup.ticketservice.model;

import java.util.Set;

/**
 * Estados del ticket y transiciones permitidas:
 *   ABIERTO    -> EN_PROCESO | CERRADO
 *   EN_PROCESO -> CERRADO
 *   CERRADO    -> (ninguno, es estado final)
 */
public enum Estado {
    ABIERTO,
    EN_PROCESO,
    CERRADO;

    public boolean puedeCambiarA(Estado nuevo) {
        return switch (this) {
            case ABIERTO -> Set.of(EN_PROCESO, CERRADO).contains(nuevo);
            case EN_PROCESO -> nuevo == CERRADO;
            case CERRADO -> false;
        };
    }
}
