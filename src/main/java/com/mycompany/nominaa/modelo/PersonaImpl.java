package com.mycompany.nominaa.modelo;

/**
 * Implementación básica de Persona
 * 
 * Técnica: Tease Apart Inheritance + Replace Inheritance with Delegation
 * 
 * Responsabilidad única:
 * - Encapsular datos de identidad (cedula, nombre)
 * 
 * Beneficios:
 * ✓ Implementación simple y directa
 * ✓ Fácil de testear
 * ✓ No hereda comportamientos innecesarios
 * 
 * Ejemplo:
 * ```java
 * Persona p = new PersonaImpl(123, "Juan");
 * int cedula = p.getCedula();        // 123
 * String nombre = p.getNombre();     // "Juan"
 * ```
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Implementación de Persona
 */
public class PersonaImpl implements Persona {
    private int cedula;
    private String nombre;

    /**
     * Constructor
     * 
     * @param cedula Cédula del empleado
     * @param nombre Nombre del empleado
     */
    public PersonaImpl(int cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    @Override
    public int getCedula() {
        return cedula;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return String.format("PersonaImpl{cedula=%d, nombre='%s'}", cedula, nombre);
    }
}
