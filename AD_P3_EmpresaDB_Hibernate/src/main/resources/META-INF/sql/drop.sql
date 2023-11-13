-- Eliminar las claves foráneas (FK) si existen
ALTER TABLE IF EXISTS empleado DROP FOREIGN KEY IF EXISTS fk_departamento;
ALTER TABLE IF EXISTS departamento DROP FOREIGN KEY IF EXISTS fk_jefe;

-- Eliminar las tablas si existen
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS departamento;