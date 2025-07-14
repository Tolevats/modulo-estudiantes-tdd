-- Script SQL
-- Proyecto TDD - Gestión de Estudiantes

-- Eliminar tabla si existe (para reiniciar)
DROP TABLE IF EXISTS estudiantes;

-- Crear tabla estudiantes
CREATE TABLE estudiantes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    edad INTEGER NOT NULL CHECK (edad >= 18 AND edad <= 100),
    curso VARCHAR(200), NOT NULL DEFAULT 'Sin asignar',
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Insertar datos de ejemplo (5 registros)
INSERT INTO estudiantes (nombre, email, edad, curso, fecha_registro, activo) VALUES
(1, 'Ana García', 'ana.garcia@email.com', 22, 'Java Básico', '2024-01-15 10:00:00', true),
(2, 'Pedro López', 'pedro.lopez@email.com', 28, 'Spring Framework', '2024-01-16 11:30:00', true),
(3, 'María Rodríguez', 'maria.rodriguez@email.com', 25, 'Testing y TDD', '2024-01-17 14:15:00', true),
(4, 'Carlos Mendoza', 'carlos.mendoza@email.com', 30, 'Microservicios', '2024-01-18 09:45:00', true),
(5, 'Laura Martínez', 'laura.martinez@email.com', 26, 'React y Frontend', '2024-01-19 16:20:00', false);

-- Consultas de verificación
SELECT 'Total de estudiantes registrados:' as descripcion, COUNT(*) as cantidad FROM estudiantes;
SELECT * FROM estudiantes ORDER BY id;

-- Consultas adicionales para testing
SELECT 'Estudiantes mayores de 18:' as descripcion, COUNT(*) as cantidad
FROM estudiantes WHERE edad >= 18;

SELECT 'Emails únicos verificación:' as descripcion, COUNT(DISTINCT email) as cantidad
FROM estudiantes;

-- Índices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_estudiantes_email ON estudiantes(email);
CREATE INDEX IF NOT EXISTS idx_estudiantes_activo ON estudiantes(activo);
CREATE INDEX IF NOT EXISTS idx_estudiantes_nombre ON estudiantes(nombre);