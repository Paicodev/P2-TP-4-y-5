CREATE DATABASE empresa_db;
USE empresa_db;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(50) NOT NULL
);
USE empresa_db;

-- 1. Creamos la tabla de departamentos
CREATE TABLE departamentos (
    id_depto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_depto VARCHAR(50) NOT NULL
);

-- Insertamos algunos datos iniciales para el ComboBox
INSERT INTO departamentos (nombre_depto) VALUES ('Sistemas'), ('Recursos Humanos'), ('Finanzas'), ('Soporte Técnico');

-- 2. Modificamos la tabla empleados (la borramos y la recreamos para aplicar las nuevas columnas y la clave foránea)
DROP TABLE IF EXISTS empleados;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_depto INT NOT NULL,
    ruta_foto VARCHAR(255),
    FOREIGN KEY (id_depto) REFERENCES departamentos(id_depto)
);