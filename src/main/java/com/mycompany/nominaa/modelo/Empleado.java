package com.mycompany.nominaa.modelo;

/**
 * Empleado - Todas las 5 técnicas de refactorización aplicadas
 * 
 * TÉCNICAS APLICADAS:
 * 
 * 1. Replace Temp with Query
 *    - Eliminadas variables temporales
 *    - Cálculo de salario en CalculadoraSalarioEmpleado
 * 
 * 2. Introduce Parameter Object
 *    - Constructor acepta DatosEmpleado
 *    - Parámetros primitivos agrupados
 * 
 * 3. Extract Class
 *    - Cálculo de salario extraído a CalculadoraSalarioEmpleado
 *    - DAO dividido en TrabajoDAO, NominaDAO
 * 
 * 4. Tease Apart Inheritance
 *    - Persona es ahora interfaz (solo identidad)
 *    - CalculadoraSalario es interfaz separada
 *    - Composición en lugar de herencia
 * 
 * 5. Replace Inheritance with Delegation
 *    - No hereda de Persona
 *    - Composición: private Persona persona
 *    - Flexible: puede cambiar Persona en runtime
 *    - Bajo acoplamiento: depende de interfaz, no implementación
 * 
 * ESTRUCTURA:
 * - Identidad: delegada a Persona (interfaz)
 * - Datos de trabajo: cedula, nombre, horas, valorHora
 * - Cálculo: delegado a CalculadoraSalario (interfaz)
 * 
 * BENEFICIOS:
 * ✓ Bajo acoplamiento: depende de interfaces
 * ✓ Flexible: puede cambiar Persona o CalculadoraSalario en runtime
 * ✓ Testeable: fácil mockear Persona y CalculadoraSalario
 * ✓ Extensible: fácil agregar nuevas implementaciones
 * ✓ Mantenible: responsabilidades claras
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Todas las 5 técnicas aplicadas
 */
public class Empleado {
    
    // DELEGACIÓN: Persona es una interfaz, no herencia
    private Persona persona;
    private int horas;
    private double valorHora;
    
    // STRATEGY: CalculadoraSalario es intercambiable
    private CalculadoraSalario calculadora;

    /**
     * Constructor - Introduce Parameter Object
     * 
     * Agrupa todos los parámetros primitivos en DatosEmpleado
     * DatosEmpleado valida automáticamente
     * 
     * @param datos Objeto con cedula, nombre, horas, valorHora
     */
    public Empleado(DatosEmpleado datos) {
        this(new PersonaImpl(datos.getCedula(), datos.getNombre()),
             datos.getHoras(),
             datos.getValorHora());
    }

    /**
     * Constructor con Persona (composición)
     * 
     * Replace Inheritance with Delegation:
     * - No hereda de Persona
     * - Recibe Persona como parámetro
     * - Flexible: puede ser PersonaImpl o PersonaExtendida
     * 
     * @param persona Interfaz Persona (implementación intercambiable)
     * @param horas Horas trabajadas
     * @param valorHora Valor por hora
     */
    public Empleado(Persona persona, int horas, double valorHora) {
        this.persona = persona;
        this.horas = horas;
        this.valorHora = valorHora;
        this.calculadora = new CalculadoraSalarioEmpleado();
    }

    /**
     * Constructor legacy - Mantiene compatibilidad hacia atrás
     * 
     * @deprecated Usar constructor con DatosEmpleado
     */
    public Empleado(int cedula, String nombre, int horas, double valorHora) {
        this(new DatosEmpleado(cedula, nombre, horas, valorHora));
    }

    // DELEGACIÓN: Métodos que delegan a persona
    public int getCedula() {
        return persona.getCedula();
    }

    public String getNombre() {
        return persona.getNombre();
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    /**
     * Replace Temp with Query + STRATEGY PATTERN
     * 
     * No hay variables temporales
     * Todo el cálculo está en CalculadoraSalario
     * 
     * @return Salario calculado según horas trabajadas
     */
    public double calcularSalario() {
        return calculadora.calcularSalario(horas, valorHora);
    }

    /**
     * Cambia la estrategia de cálculo en runtime
     * Beneficio de composition over inheritance
     * 
     * @param calculadora Nueva estrategia de cálculo
     */
    public void setCalculadora(CalculadoraSalario calculadora) {
        this.calculadora = calculadora;
    }

    /**
     * Cambia la Persona en runtime
     * Beneficio de delegation over inheritance
     * 
     * @param persona Nueva persona (PersonaImpl, PersonaExtendida, etc.)
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Acceso directo a Persona
     * Útil para acceder a métodos específicos de implementaciones
     */
    public Persona getPersona() {
        return persona;
    }

    @Override
    public String toString() {
        return String.format("Empleado{cedula=%d, nombre='%s', horas=%d, valorHora=%.2f, salario=%.2f}",
                getCedula(), getNombre(), horas, valorHora, calcularSalario());
    }
}
