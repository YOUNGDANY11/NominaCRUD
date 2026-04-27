/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nominaa.dao;

/**
 *
 * @author Dany
 */
import com.mycompany.nominaa.conexion.ConexionDB;
import com.mycompany.nominaa.modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDAO {

    Connection con;

    public EmpleadoDAO(){
        con = ConexionDB.conectar();
    }
    
    // INSERTAR
    public boolean insertar(Empleado e){
        try{

            if(existeCedula(e.getCedula())){
                return false;
            }

            PreparedStatement ps1 = con.prepareStatement(
                    "INSERT INTO empleado VALUES(?,?)");

            ps1.setInt(1,e.getCedula());
            ps1.setString(2,e.getNombre());
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO trabajo(cedula,horas,valorHora) VALUES(?,?,?)");

            ps2.setInt(1,e.getCedula());
            ps2.setInt(2,e.getHoras());
            ps2.setDouble(3,e.getValorHora());
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(
                    "INSERT INTO nomina(cedula,salario) VALUES(?,?)");

            ps3.setInt(1,e.getCedula());
            ps3.setDouble(2,e.calcularSalario());
            ps3.executeUpdate();

            return true;

        }catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    // LISTAR
    public ArrayList<String> listar(){

        ArrayList<String> lista = new ArrayList<>();

        try{

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT e.cedula,e.nombre,t.horas,t.valorHora,n.salario " +
                    "FROM empleado e " +
                    "JOIN trabajo t ON e.cedula=t.cedula " +
                    "JOIN nomina n ON e.cedula=n.cedula");

            while(rs.next()){

                lista.add(
                        rs.getInt("cedula")+" | "+
                        rs.getString("nombre")+" | "+
                        rs.getInt("horas")+" horas | "+
                        rs.getDouble("salario")
                );
            }

        }catch(Exception ex){
            System.out.println(ex);
        }

        return lista;
    }

    // BUSCAR POR NOMBRE
    public ArrayList<String> buscarPorNombre(String nombre){

        ArrayList<String> lista = new ArrayList<>();

        try{

            PreparedStatement ps = con.prepareStatement(
                    "SELECT e.cedula,e.nombre,t.horas,n.salario " +
                    "FROM empleado e " +
                    "JOIN trabajo t ON e.cedula=t.cedula " +
                    "JOIN nomina n ON e.cedula=n.cedula " +
                    "WHERE e.nombre LIKE ?");

            ps.setString(1,"%"+nombre+"%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                lista.add(
                        rs.getInt("cedula")+" | "+
                        rs.getString("nombre")+" | "+
                        rs.getInt("horas")+" horas | "+
                        rs.getDouble("salario")
                );
            }

        }catch(Exception ex){
            System.out.println(ex);
        }

        return lista;
    }
    
    // BUSCAR POR NOMBRE
    public ArrayList<String> buscarPorCedula(int nombre){

        ArrayList<String> lista = new ArrayList<>();

        try{

            PreparedStatement ps = con.prepareStatement(
                    "SELECT e.cedula,e.nombre,t.horas,n.salario " +
                    "FROM empleado e " +
                    "JOIN trabajo t ON e.cedula=t.cedula " +
                    "JOIN nomina n ON e.cedula=n.cedula " +
                    "WHERE e.cedula LIKE ?");

            ps.setString(1,"%"+nombre+"%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                lista.add(
                        rs.getInt("cedula")+" | "+
                        rs.getString("nombre")+" | "+
                        rs.getInt("horas")+" horas | "+
                        rs.getDouble("salario")
                );
            }

        }catch(Exception ex){
            System.out.println(ex);
        }

        return lista;
    }
    
    //Ver si la cedula existe
    public boolean existeCedula(int cedula){

        boolean existe = false;

        try{

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM empleado WHERE cedula=?");

            ps.setInt(1, cedula);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                existe = true;
            }

        }catch(Exception e){
            System.out.println(e);
        }

        return existe;
    }
    // ACTUALIZAR
    public boolean actualizar(Empleado e){

        try{

            if(!existeCedula(e.getCedula())){
                return false;
            }

            PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE empleado SET nombre=? WHERE cedula=?");

            ps1.setString(1,e.getNombre());
            ps1.setInt(2,e.getCedula());
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE trabajo SET horas=?, valorHora=? WHERE cedula=?");

            ps2.setInt(1,e.getHoras());
            ps2.setDouble(2,e.getValorHora());
            ps2.setInt(3,e.getCedula());
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(
                    "UPDATE nomina SET salario=? WHERE cedula=?");

            ps3.setDouble(1,e.calcularSalario());
            ps3.setInt(2,e.getCedula());
            ps3.executeUpdate();

            return true;

        }catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    // ELIMINAR
    public boolean eliminar(int cedula){

        try{

            if(!existeCedula(cedula)){
                return false;
            }

            PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM nomina WHERE cedula=?");

            ps1.setInt(1,cedula);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM trabajo WHERE cedula=?");

            ps2.setInt(1,cedula);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(
                    "DELETE FROM empleado WHERE cedula=?");

            ps3.setInt(1,cedula);
            ps3.executeUpdate();

            return true;

        }catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }
}