package com.mycompany.nominaa.modelo;

/**
 * Interfaz Persona - Tease Apart Inheritance
 * 
 * Técnica: Tease Apart Inheritance
 * 
 * Antes: abstract class Persona mezclaba 2 responsabilidades
 * 1. Identidad (cedula, nombre)
 * 2. Nómina (calcularSalario)
 * 
 * Después: interface Persona solo maneja identidad
 * - La responsabilidad de nómina está en CalculadoraSalario
 * - La responsabilidad de composición está en Empleado
 * 
 * Beneficios:
 * ✓ SRP: Cada interfaz una responsabilidad
 * ✓ Múltiples implementaciones (PersonaImpl, PersonaExtendida)
 * ✓ Bajo acoplamiento
 * ✓ Flexible: puede cambiar en runtime
 * 
 * Implementaciones:
 * - PersonaImpl: cedula + nombre básico
 * - PersonaExtendida: cedula + nombre + email + telefono
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Tease Apart Inheritance
 */
public interface Persona {
    /**
     * @return Cédula del empleado
     */
    int getCedula();

    /**
     * @return Nombre del empleado
     */
    String getNombre();
}
