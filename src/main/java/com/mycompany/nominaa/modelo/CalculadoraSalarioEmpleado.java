package com.mycompany.nominaa.modelo;

/**
 * Calculadora de Salario para Empleados
 * 
 * Técnica: Replace Temp with Query
 * 
 * Antes: 4 variables temporales en un método
 * - double salario = 0
 * - int extras = ...
 * - int extrasSimples = ...
 * - int extrasDobles = ...
 * 
 * Después: 5 métodos queries (uno por concepto)
 * - sonHorasRegulares()
 * - sonHorasConExtras()
 * - calcularSalarioRegular()
 * - calcularSalarioConExtras()
 * - calcularSalarioConExtrasDobles()
 * 
 * Fórmula de cálculo:
 * - 0-40 horas: valor * horas
 * - 40-48 horas: (40 * valor) + (extras * valor * 1.2)
 * - >48 horas: (40 * valor) + (8 * valor * 1.2) + (dobles * valor * 1.4)
 * 
 * Beneficios:
 * ✓ Métodos pequeños: cada uno hace UNA cosa
 * ✓ Legible: nombres descriptivos
 * ✓ Testeable: cada método es independiente
 * ✓ Reutilizable: métodos pueden usarse en otros contextos
 * ✓ Mantenible: fácil entender y modificar
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Replace Temp with Query
 */
public class CalculadoraSalarioEmpleado implements CalculadoraSalario {

    @Override
    public double calcularSalario(int horas, double valorHora) {
        if (sonHorasRegulares(horas)) {
            return calcularSalarioRegular(horas, valorHora);
        } else if (sonHorasConExtras(horas)) {
            return calcularSalarioConExtras(horas, valorHora);
        } else {
            return calcularSalarioConExtrasDobles(horas, valorHora);
        }
    }

    /**
     * Query: ¿Son horas regulares (0-40)?
     */
    private boolean sonHorasRegulares(int horas) {
        return horas <= 40;
    }

    /**
     * Query: ¿Son horas con extras simples (40-48)?
     */
    private boolean sonHorasConExtras(int horas) {
        return horas <= 48;
    }

    /**
     * Calcula salario para horas regulares (0-40)
     * Fórmula: horas * valorHora
     */
    private double calcularSalarioRegular(int horas, double valorHora) {
        return horas * valorHora;
    }

    /**
     * Calcula salario para horas con extras simples (40-48)
     * Fórmula:
     * - 40 horas normales: 40 * valorHora
     * - Extras al 20%: (horas - 40) * valorHora * 1.2
     */
    private double calcularSalarioConExtras(int horas, double valorHora) {
        int extrasHoras = horas - 40;
        double horasNormales = 40 * valorHora;
        double horasExtras = extrasHoras * valorHora * 1.2;
        return horasNormales + horasExtras;
    }

    /**
     * Calcula salario para horas con extras dobles (>48)
     * Fórmula:
     * - 40 horas normales: 40 * valorHora
     * - 8 extras simples (40-48): 8 * valorHora * 1.2
     * - Extras dobles (>48): (horas - 48) * valorHora * 1.4
     */
    private double calcularSalarioConExtrasDobles(int horas, double valorHora) {
        double horasNormales = 40 * valorHora;
        double horasExtrasSimples = 8 * valorHora * 1.2;
        double horasExtrasDobles = (horas - 48) * valorHora * 1.4;
        return horasNormales + horasExtrasSimples + horasExtrasDobles;
    }
}
