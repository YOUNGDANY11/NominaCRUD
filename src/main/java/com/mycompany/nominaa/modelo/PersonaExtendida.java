package com.mycompany.nominaa.modelo;

/**
 * Implementación extendida de Persona
 * 
 * Técnica: Tease Apart Inheritance + Open/Closed Principle
 * 
 * Añade más datos además de cédula y nombre:
 * - email: Correo electrónico
 * - telefono: Teléfono de contacto
 * 
 * Beneficios:
 * ✓ Demuestra la extensibilidad sin modificar código existente
 * ✓ Open/Closed: Abierto a extensión, cerrado a modificación
 * ✓ Empleado funciona con cualquier Persona (Liskov Substitution)
 * 
 * Ejemplo:
 * ```java
 * Persona p = new PersonaExtendida(123, "Juan", "juan@mail.com", "123456789");
 * Empleado emp = new Empleado(p, 40, 15.5);
 * ```
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Implementación extendida de Persona
 */
public class PersonaExtendida implements Persona {
    private int cedula;
    private String nombre;
    private String email;
    private String telefono;

    /**
     * Constructor con datos extendidos
     * 
     * @param cedula Cédula del empleado
     * @param nombre Nombre del empleado
     * @param email Correo electrónico
     * @param telefono Teléfono de contacto
     */
    public PersonaExtendida(int cedula, String nombre, String email, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    @Override
    public int getCedula() {
        return cedula;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return String.format("PersonaExtendida{cedula=%d, nombre='%s', email='%s', telefono='%s'}",
                cedula, nombre, email, telefono);
    }
}
