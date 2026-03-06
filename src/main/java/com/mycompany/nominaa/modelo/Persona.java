/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nominaa.modelo;

/**
 *
 * @author Dany
 */
public abstract class Persona {

    protected int cedula;
    protected String nombre;

    public Persona(int cedula, String nombre){
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public int getCedula(){
        return cedula;
    }

    public String getNombre(){
        return nombre;
    }

    public abstract double calcularSalario();
}