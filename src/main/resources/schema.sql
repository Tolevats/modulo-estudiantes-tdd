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
INSERT INTO estudiantes (nombre, email, edad, curso) VALUES
    ('Juana Quintana', 'jquintana@mail.com', 33, 'Testing 101'),
    ('Carlos Muro', 'cmuro@mail.com', 28, 'Java'),
    ('Ninfa Manríquez', 'nmanriquez@mail.com', 24, 'Testing Automation I'),
    ('María Sánchez', 'msanchez@mail.com', 21, 'Scrum Fundamentals'),
    ('Hugo Ramírez', 'hramirez@mail.com', 23, 'Testing Automation II');

-- Consultas de verificación
SELECT 'Total de estudiantes registrados:' as descripcion, COUNT(*) as cantidad FROM estudiantes;
SELECT * FROM estudiantes ORDER BY id;

-- Consultas adicionales para testing
SELECT 'Estudiantes mayores de 18:' as descripcion, COUNT(*) as cantidad
FROM estudiantes WHERE edad >= 18;

SELECT 'Emails únicos verificación:' as descripcion, COUNT(DISTINCT email) as cantidad
FROM estudiantes;