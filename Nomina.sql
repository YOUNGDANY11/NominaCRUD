CREATE DATABASE nomina;

USE nomina;

CREATE TABLE empleado(
    cedula INT PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE trabajo(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula INT,
    horas INT,
    valorHora DOUBLE,
    FOREIGN KEY(cedula) REFERENCES empleado(cedula)
);

CREATE TABLE nomina(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula INT,
    salario DOUBLE,
    FOREIGN KEY(cedula) REFERENCES empleado(cedula)
);