package com.mycompany.nominaa.modelo;

/**
 * Objeto Parámetro - Introduce Parameter Object
 * 
 * Técnica: Introduce Parameter Object
 * 
 * Antes: 4 parámetros primitivos dispersos
 * - int cedula
 * - String nombre
 * - int horas
 * - double valorHora
 * 
 * Después: 1 objeto que agrupa todo
 * - DatosEmpleado datos
 * 
 * Beneficios:
 * ✓ Validación centralizada en un lugar
 * ✓ Fácil agregar nuevos campos
 * ✓ Mejor legibilidad
 * ✓ Reutilizable en múltiples métodos
 * ✓ SOLID: SRP (responsabilidad única) + DIP (depender de abstracciones)
 * 
 * Ejemplo de uso:
 * ```java
 * DatosEmpleado datos = new DatosEmpleado(123, "Juan", 40, 15.5);
 * Empleado emp = new Empleado(datos);
 * ```
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Introduce Parameter Object
 */
public class DatosEmpleado {
    private int cedula;
    private String nombre;
    private int horas;
    private double valorHora;

    /**
     * Constructor - Valida automáticamente todos los datos
     * 
     * @param cedula Cédula del empleado (debe ser positiva)
     * @param nombre Nombre del empleado (no puede estar vacío)
     * @param horas Horas trabajadas (no pueden ser negativas)
     * @param valorHora Valor por hora (debe ser positivo)
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public DatosEmpleado(int cedula, String nombre, int horas, double valorHora) {
        validar(cedula, nombre, horas, valorHora);
        this.cedula = cedula;
        this.nombre = nombre;
        this.horas = horas;
        this.valorHora = valorHora;
    }

    /**
     * Valida todos los parámetros
     * 
     * Validaciones:
     * - cedula > 0
     * - nombre no nulo ni vacío
     * - horas >= 0
     * - valorHora > 0
     */
    private void validar(int cedula, String nombre, int horas, double valorHora) {
        if (cedula <= 0) {
            throw new IllegalArgumentException("❌ Cédula debe ser positiva: " + cedula);
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Nombre no puede estar vacío");
        }
        if (horas < 0) {
            throw new IllegalArgumentException("❌ Horas no pueden ser negativas: " + horas);
        }
        if (valorHora <= 0) {
            throw new IllegalArgumentException("❌ Valor hora debe ser positivo: " + valorHora);
        }
    }

    // Getters
    public int getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public int getHoras() { return horas; }
    public double getValorHora() { return valorHora; }

    @Override
    public String toString() {
        return String.format("DatosEmpleado{cedula=%d, nombre='%s', horas=%d, valorHora=%.2f}",
                cedula, nombre, horas, valorHora);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DatosEmpleado other = (DatosEmpleado) obj;
        return cedula == other.cedula &&
               nombre.equals(other.nombre) &&
               horas == other.horas &&
               Double.compare(valorHora, other.valorHora) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cedula, nombre, horas, valorHora);
    }
}
