/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nominaa.modelo;

/**
 *
 * @author Dany
 */
public class Empleado extends Persona {

    private int horas;
    private double valorHora;

    public Empleado(int cedula, String nombre, int horas, double valorHora) {
        super(cedula, nombre);
        this.horas = horas;
        this.valorHora = valorHora;
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

    @Override
    public double calcularSalario() {

        double salario = 0;

        if(horas <= 40){

            salario = horas * valorHora;

        }else if(horas <= 48){

            int extras = horas - 40;

            salario = (40 * valorHora) +
                    (extras * valorHora * 1.2);

        }else{

            int extrasSimples = 8;
            int extrasDobles = horas - 48;

            salario = (40 * valorHora) +
                    (extrasSimples * valorHora * 1.2) +
                    (extrasDobles * valorHora * 1.4);
        }

        return salario;
    }
}
