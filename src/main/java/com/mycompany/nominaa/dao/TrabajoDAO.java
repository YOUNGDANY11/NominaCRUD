package com.mycompany.nominaa.dao;

import com.mycompany.nominaa.conexion.ConexionDB;
import com.mycompany.nominaa.modelo.Empleado;
import java.sql.*;

/**
 * DAO para operaciones sobre la tabla 'trabajo'
 * 
 * Técnica: Extract Class
 * 
 * Responsabilidad única:
 * - Operaciones CRUD en tabla 'trabajo' (horas, valorHora)
 * - NO responsable de tabla 'empleado' o 'nomina'
 * 
 * Antes:
 * - Responsabilidad: incluida en EmpleadoDAO
 * - 264 líneas de código
 * - Baja cohesión
 * 
 * Después:
 * - Responsabilidad: tabla 'trabajo' SOLAMENTE
 * - ~60 líneas de código
 * - Alta cohesión
 * 
 * SOLID Principles:
 * - S: Una responsabilidad única (tabla trabajo)
 * - D: Independiente de otros DAOs
 * 
 * Uso:
 * ```java
 * TrabajoDAO dao = new TrabajoDAO();
 * dao.insertar(empleado);  // INSERT INTO trabajo
 * dao.actualizar(empleado);  // UPDATE trabajo
 * dao.eliminar(cedula);  // DELETE FROM trabajo
 * ```
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Extract Class
 */
public class TrabajoDAO {
    private Connection con;

    /**
     * Constructor - Obtiene conexión a BD
     */
    public TrabajoDAO() {
        this.con = ConexionDB.conectar();
    }

    /**
     * INSERT - Inserta datos de trabajo
     * 
     * @param e Empleado con datos de trabajo
     * @return true si fue exitoso, false si hubo error
     */
    public boolean insertar(Empleado e) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO trabajo(cedula,horas,valorHora) VALUES(?,?,?)");
            ps.setInt(1, e.getCedula());
            ps.setInt(2, e.getHoras());
            ps.setDouble(3, e.getValorHora());
            ps.executeUpdate();
            return true;
        } catch(Exception ex) {
            System.out.println("❌ Error en TrabajoDAO.insertar(): " + ex);
            return false;
        }
    }

    /**
     * UPDATE - Actualiza horas y valor hora
     * 
     * @param e Empleado con nuevos datos
     * @return true si fue exitoso, false si hubo error
     */
    public boolean actualizar(Empleado e) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE trabajo SET horas=?, valorHora=? WHERE cedula=?");
            ps.setInt(1, e.getHoras());
            ps.setDouble(2, e.getValorHora());
            ps.setInt(3, e.getCedula());
            ps.executeUpdate();
            return true;
        } catch(Exception ex) {
            System.out.println("❌ Error en TrabajoDAO.actualizar(): " + ex);
            return false;
        }
    }

    /**
     * DELETE - Elimina registro de trabajo
     * 
     * ⚠️ IMPORTANTE: Respetar orden de eliminación por claves foráneas
     * Orden correcto:
     * 1. NominaDAO.eliminar(cedula)  - primero
     * 2. TrabajoDAO.eliminar(cedula) - segundo
     * 3. EmpleadoDAO.eliminar(cedula) - tercero
     * 
     * @param cedula Cédula del empleado
     * @return true si fue exitoso, false si hubo error
     */
    public boolean eliminar(int cedula) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM trabajo WHERE cedula=?");
            ps.setInt(1, cedula);
            ps.executeUpdate();
            return true;
        } catch(Exception ex) {
            System.out.println("❌ Error en TrabajoDAO.eliminar(): " + ex);
            return false;
        }
    }
}
