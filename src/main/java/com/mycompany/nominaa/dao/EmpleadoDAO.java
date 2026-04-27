package com.mycompany.nominaa.dao;

import com.mycompany.nominaa.conexion.ConexionDB;
import com.mycompany.nominaa.modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO responsable de operaciones sobre la tabla 'empleado'
 * 
 * Técnica: Extract Class
 * 
 * Antes: EmpleadoDAO manejaba 3 tablas (empleado, trabajo, nomina) - Baja cohesión
 * Después: EmpleadoDAO maneja SOLO tabla 'empleado' + coordina otros DAOs
 * 
 * Responsabilidad única:
 * - Operaciones CRUD en tabla 'empleado' (cedula, nombre)
 * - Coordinación con TrabajoDAO y NominaDAO
 * - Consultas complejas (JOIN entre tablas)
 * 
 * SOLID Principles:
 * - S: Una responsabilidad primaria (tabla empleado)
 * - O: Cerrado a modificación, abierto a extensión
 * - D: Depende de TrabajoDAO y NominaDAO (abstracciones)
 * 
 * ORDEN IMPORTANTE en operaciones:
 * 
 * INSERTAR:
 * 1. empleadoDAO.insertar() → tabla empleado
 * 2. trabajoDAO.insertar() → tabla trabajo
 * 3. nominaDAO.insertar() → tabla nomina
 * 
 * ELIMINAR (inverso por claves foráneas):
 * 1. nominaDAO.eliminar() → tabla nomina (primero)
 * 2. trabajoDAO.eliminar() → tabla trabajo (segundo)
 * 3. empleadoDAO.eliminar() → tabla empleado (tercero)
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Refactorizado: Extract Class
 */
public class EmpleadoDAO {
    private Connection con;
    private TrabajoDAO trabajoDAO;
    private NominaDAO nominaDAO;

    /**
     * Constructor - inicializa DAOs y conexión
     */
    public EmpleadoDAO(){
        this.con = ConexionDB.conectar();
        this.trabajoDAO = new TrabajoDAO();
        this.nominaDAO = new NominaDAO();
    }
    
    /**
     * INSERTAR - Coordina inserción en 3 tablas
     * 
     * Orden importante:
     * 1. Tabla empleado (clave primaria)
     * 2. Tabla trabajo (clave foránea a empleado)
     * 3. Tabla nomina (clave foránea a empleado)
     * 
     * @param e Empleado a insertar
     * @return true si fue exitoso, false si hubo error
     */
    public boolean insertar(Empleado e){
        try{

            if(existeCedula(e.getCedula())){
                return false;
            }

            // 1. Insertar en tabla empleado
            PreparedStatement ps1 = con.prepareStatement(
                    "INSERT INTO empleado VALUES(?,?)");

            ps1.setInt(1,e.getCedula());
            ps1.setString(2,e.getNombre());
            ps1.executeUpdate();

            // 2. Delega a TrabajoDAO - INSERT en tabla trabajo
            if(!trabajoDAO.insertar(e)) {
                return false;
            }

            // 3. Delega a NominaDAO - INSERT en tabla nomina
            if(!nominaDAO.insertar(e)) {
                return false;
            }

            return true;

        }catch(Exception ex){
            System.out.println("❌ Error en EmpleadoDAO.insertar(): " + ex);
        }

        return false;
    }

    /**
     * LISTAR - Consulta compleja con JOINs
     * 
     * @return Lista de empleados con sus datos de trabajo y salario
     */
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
            System.out.println("❌ Error en EmpleadoDAO.listar(): " + ex);
        }

        return lista;
    }

    /**
     * BUSCAR POR NOMBRE
     * 
     * @param nombre nombre o parte del nombre a buscar
     * @return Lista de empleados coincidentes
     */
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
            System.out.println("❌ Error en EmpleadoDAO.buscarPorNombre(): " + ex);
        }

        return lista;
    }
    
    /**
     * BUSCAR POR CÉDULA
     * 
     * @param cedula cédula a buscar
     * @return Lista con un empleado (si existe)
     */
    public ArrayList<String> buscarPorCedula(int cedula){

        ArrayList<String> lista = new ArrayList<>();

        try{

            PreparedStatement ps = con.prepareStatement(
                    "SELECT e.cedula,e.nombre,t.horas,n.salario " +
                    "FROM empleado e " +
                    "JOIN trabajo t ON e.cedula=t.cedula " +
                    "JOIN nomina n ON e.cedula=n.cedula " +
                    "WHERE e.cedula LIKE ?");

            ps.setString(1,"%"+cedula+"%");

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
            System.out.println("❌ Error en EmpleadoDAO.buscarPorCedula(): " + ex);
        }

        return lista;
    }
    
    /**
     * Ver si la cédula existe en la tabla empleado
     * 
     * @param cedula cédula a verificar
     * @return true si existe, false si no existe
     */
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
            System.out.println("❌ Error en EmpleadoDAO.existeCedula(): " + e);
        }

        return existe;
    }

    /**
     * ACTUALIZAR - Coordina actualización en 3 tablas
     * 
     * Orden importante:
     * 1. Tabla empleado
     * 2. Tabla trabajo
     * 3. Tabla nomina (recalcula salario)
     * 
     * @param e Empleado a actualizar
     * @return true si fue exitoso, false si hubo error
     */
    public boolean actualizar(Empleado e){

        try{

            if(!existeCedula(e.getCedula())){
                return false;
            }

            // 1. Actualizar en tabla empleado
            PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE empleado SET nombre=? WHERE cedula=?");

            ps1.setString(1,e.getNombre());
            ps1.setInt(2,e.getCedula());
            ps1.executeUpdate();

            // 2. Delega a TrabajoDAO - UPDATE en tabla trabajo
            if(!trabajoDAO.actualizar(e)) {
                return false;
            }

            // 3. Delega a NominaDAO - UPDATE en tabla nomina (recalcula)
            if(!nominaDAO.actualizar(e)) {
                return false;
            }

            return true;

        }catch(Exception ex){
            System.out.println("❌ Error en EmpleadoDAO.actualizar(): " + ex);
        }

        return false;
    }

    /**
     * ELIMINAR - Coordina eliminación en 3 tablas
     * 
     * ⚠️ ORDEN IMPORTANTE por claves foráneas:
     * 1. Tabla nomina (referencia a empleado)
     * 2. Tabla trabajo (referencia a empleado)
     * 3. Tabla empleado (tabla principal)
     * 
     * @param cedula Cédula del empleado a eliminar
     * @return true si fue exitoso, false si hubo error
     */
    public boolean eliminar(int cedula){

        try{

            if(!existeCedula(cedula)){
                return false;
            }

            // ⚠️ ORDEN IMPORTANTE: Respetar claves foráneas
            
            // 1. Delega a NominaDAO - DELETE en tabla nomina PRIMERO
            if(!nominaDAO.eliminar(cedula)) {
                return false;
            }

            // 2. Delega a TrabajoDAO - DELETE en tabla trabajo SEGUNDO
            if(!trabajoDAO.eliminar(cedula)) {
                return false;
            }

            // 3. DELETE en tabla empleado TERCERO
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM empleado WHERE cedula=?");

            ps.setInt(1,cedula);
            ps.executeUpdate();

            return true;

        }catch(Exception ex){
            System.out.println("❌ Error en EmpleadoDAO.eliminar(): " + ex);
        }

        return false;
    }
}
