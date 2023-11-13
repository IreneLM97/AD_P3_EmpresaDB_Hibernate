CREATE TABLE departamento (id varchar(36) NOT NULL PRIMARY KEY, nombre varchar(50) NOT NULL UNIQUE, jefe varchar(36) DEFAULT NULL);
CREATE TABLE empleado (id varchar(36) NOT NULL PRIMARY KEY, nombre varchar(50) NOT NULL, salario decimal(10,2) DEFAULT NULL, departamento varchar(36) DEFAULT NULL);
ALTER TABLE departamento ADD CONSTRAINT fk_jefe FOREIGN KEY (jefe) REFERENCES empleado (id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE empleado ADD CONSTRAINT fk_departamento FOREIGN KEY (departamento) REFERENCES departamento (id) ON DELETE SET NULL ON UPDATE CASCADE;