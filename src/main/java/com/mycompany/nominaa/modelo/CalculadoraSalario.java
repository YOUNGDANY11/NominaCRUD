package com.mycompany.nominaa.modelo;

/**
 * Interfaz Strategy para cálculo de salario
 * 
 * Técnica: Tease Apart Inheritance
 * 
 * Separa la responsabilidad de "cálculo de salario" de la responsabilidad de "datos de identidad"
 * 
 * Beneficios:
 * ✓ Strategy Pattern: diferentes formas de calcular
 * ✓ Flexible: cambiar estrategia en runtime
 * ✓ SRP: una interfaz = una responsabilidad
 * ✓ Testeable: fácil de mockear
 * 
 * Implementaciones:
 * - CalculadoraSalarioEmpleado: para empleados por horas
 * - CalculadoraSalarioConsultor: para consultores (ejemplo futuro)
 * - CalculadoraSalarioContratista: para contratistas (ejemplo futuro)
 * 
 * Ejemplo:
 * ```java
 * CalculadoraSalario calc1 = new CalculadoraSalarioEmpleado();
 * double salario1 = calc1.calcularSalario(40, 15.5); // Empleado
 * 
 * CalculadoraSalario calc2 = (horas, valor) -> horas * valor * 1.5;
 * double salario2 = calc2.calcularSalario(40, 15.5); // Consultor (+50%)
 * ```
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Tease Apart Inheritance
 */
public interface CalculadoraSalario {
    /**
     * Calcula el salario basado en horas y valor hora
     * 
     * @param horas Horas trabajadas
     * @param valorHora Valor de la hora de trabajo
     * @return Salario calculado
     */
    double calcularSalario(int horas, double valorHora);
}
