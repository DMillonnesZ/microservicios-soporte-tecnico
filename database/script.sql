-- =========================================================
--  Sistema de Soporte Tecnico - Script MariaDB (XAMPP)
-- =========================================================

-- 1) bd_usuario (usuario-service : 8081)
CREATE DATABASE IF NOT EXISTS bd_usuario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_usuario;

CREATE TABLE IF NOT EXISTS usuarios (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    nombre              VARCHAR(100) NOT NULL,
    email               VARCHAR(120) NOT NULL,
    telefono            VARCHAR(9),
    area                VARCHAR(60)  NOT NULL,
    verificado          BIT          NOT NULL DEFAULT 0,
    codigo_verificacion VARCHAR(6),
    codigo_expiracion   DATETIME(6),
    fecha_registro      DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_email UNIQUE (email)
);

INSERT IGNORE INTO usuarios (id, nombre, email, telefono, area, verificado, codigo_verificacion, codigo_expiracion, fecha_registro) VALUES
(1, 'Ana Torres Quispe', 'ana.torres@empresa.com', '987654321', 'Contabilidad', 1, NULL, NULL, NOW()),
(2, 'Luis Ramos Huaman', 'luis.ramos@empresa.com', '976543210', 'Ventas', 1, NULL, NULL, NOW()),
(3, 'Carla Mendoza Rios', 'carla.mendoza@empresa.com', '965432109', 'Recursos Humanos', 1, NULL, NULL, NOW()),
(4, 'Jorge Salas Vega', 'jorge.salas@empresa.com', '954321098', 'Logistica', 0, '123456', DATE_ADD(NOW(), INTERVAL 7 DAY), NOW());

-- 2) bd_tecnico (tecnico-service : 8082)
CREATE DATABASE IF NOT EXISTS bd_tecnico CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_tecnico;

CREATE TABLE IF NOT EXISTS tecnicos (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(100) NOT NULL,
    email          VARCHAR(120) NOT NULL,
    telefono       VARCHAR(9),
    especialidad   VARCHAR(60)  NOT NULL,
    fecha_registro DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_tecnicos_email UNIQUE (email)
);

INSERT IGNORE INTO tecnicos (id, nombre, email, telefono, especialidad, fecha_registro) VALUES
(1, 'Pedro Castillo Lazo', 'pedro.castillo@soporte.com', '912345678', 'Redes', NOW()),
(2, 'Rosa Paredes Cruz', 'rosa.paredes@soporte.com', '923456789', 'Hardware', NOW()),
(3, 'Diego Flores Ruiz', 'diego.flores@soporte.com', '934567890', 'Software', NOW());

-- 3) bd_ticket (ticket-service : 8083)
CREATE DATABASE IF NOT EXISTS bd_ticket CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_ticket;

CREATE TABLE IF NOT EXISTS tickets (
    id                  BIGINT        NOT NULL AUTO_INCREMENT,
    id_usuario          BIGINT        NOT NULL,
    id_tecnico          BIGINT        NOT NULL,
    asunto              VARCHAR(150)  NOT NULL,
    descripcion         VARCHAR(1000) NOT NULL,
    prioridad           ENUM('BAJA','MEDIA','ALTA') NOT NULL,
    estado              ENUM('ABIERTO','EN_PROCESO','CERRADO') NOT NULL,
    fecha_creacion      DATETIME(6)   NOT NULL,
    fecha_actualizacion DATETIME(6)   NOT NULL,
    fecha_cierre        DATETIME(6),
    PRIMARY KEY (id)
);

INSERT IGNORE INTO tickets (id, id_usuario, id_tecnico, asunto, descripcion, prioridad, estado, fecha_creacion, fecha_actualizacion, fecha_cierre) VALUES
(1, 1, 1, 'Sin conexion a internet', 'La PC del area de Contabilidad no tiene acceso a internet desde la manana.', 'ALTA', 'ABIERTO', NOW(), NOW(), NULL),
(2, 2, 2, 'Impresora no imprime', 'La impresora del segundo piso muestra error de atasco de papel constante.', 'MEDIA', 'EN_PROCESO', NOW(), NOW(), NULL),
(3, 3, 3, 'Error al abrir el ERP', 'El sistema ERP se cierra al intentar generar el reporte mensual de planillas.', 'ALTA', 'ABIERTO', NOW(), NOW(), NULL),
(4, 1, 2, 'Cambio de mouse', 'El mouse de la estacion 12 dejo de funcionar y se reemplazo por uno nuevo.', 'BAJA', 'CERRADO', NOW(), NOW(), NOW());

-- Verificacion
SELECT 'bd_usuario' AS bd, COUNT(*) AS registros FROM bd_usuario.usuarios
UNION ALL SELECT 'bd_tecnico', COUNT(*) FROM bd_tecnico.tecnicos
UNION ALL SELECT 'bd_ticket', COUNT(*) FROM bd_ticket.tickets;

COMMIT;