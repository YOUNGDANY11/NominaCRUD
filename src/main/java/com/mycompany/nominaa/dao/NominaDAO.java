package com.mycompany.nominaa.dao;

import com.mycompany.nominaa.conexion.ConexionDB;
import com.mycompany.nominaa.modelo.Empleado;
import java.sql.*;

/**
 * DAO para operaciones sobre la tabla 'nomina'
 * 
 * Técnica: Extract Class
 * 
 * Responsabilidad única:
 * - Operaciones CRUD en tabla 'nomina' (salario calculado)
 * - NO responsable de tabla 'empleado' o 'trabajo'
 * 
 * Antes:
 * - Responsabilidad: incluida en EmpleadoDAO
 * - 264 líneas de código
 * - Baja cohesión
 * 
 * Después:
 * - Responsabilidad: tabla 'nomina' SOLAMENTE
 * - ~60 líneas de código
 * - Alta cohesión
 * 
 * SOLID Principles:
 * - S: Una responsabilidad única (tabla nomina)
 * - D: Independiente de otros DAOs
 * 
 * Nota importante:
 * - El salario se calcula automáticamente: empleado.calcularSalario()
 * - No hay input del usuario para el salario
 * - Es derivado de horas * valorHora con extras
 * 
 * Uso:
 * ```java
 * NominaDAO dao = new NominaDAO();
 * dao.insertar(empleado);  // INSERT INTO nomina (salario calculado)
 * dao.actualizar(empleado);  // UPDATE nomina (recalcula salario)
 * dao.eliminar(cedula);  // DELETE FROM nomina
 * ```
 * 
 * @author YOUNGDANY11
 * @version 2.0 - Extract Class
 */
public class NominaDAO {
    private Connection con;

    /**
     * Constructor - Obtiene conexión a BD
     */
    public NominaDAO() {
        this.con = ConexionDB.conectar();
    }

    /**
     * INSERT - Inserta registro de nómina
     * 
     * El salario se calcula automáticamente via empleado.calcularSalario()
     * 
     * @param e Empleado (el salario se calcula de sus horas y valorHora)
     * @return true si fue exitoso, false si hubo error
     */
    public boolean insertar(Empleado e) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO nomina(cedula,salario) VALUES(?,?)");
            ps.setInt(1, e.getCedula());
            ps.setDouble(2, e.calcularSalario());
            ps.executeUpdate();
            return true;
        } catch(Exception ex) {
            System.out.println("❌ Error en NominaDAO.insertar(): " + ex);
            return false;
        }
    }

    /**
     * UPDATE - Actualiza salario (recalculado automáticamente)
     * 
     * Se usa cuando cambian las horas o valorHora de un empleado
     * El salario se recalcula con la fórmula:
     * - 0-40 horas: horas * valorHora
     * - 40-48 horas: (40*valor) + (extras*valor*1.2)
     * - >48 horas: (40*valor) + (8*valor*1.2) + (dobles*valor*1.4)
     * 
     * @param e Empleado con nuevas horas/valorHora
     * @return true si fue exitoso, false si hubo error
     */
    public boolean actualizar(Empleado e) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE nomina SET salario=? WHERE cedula=?");
            ps.setDouble(1, e.calcularSalario());
            ps.setInt(2, e.getCedula());
            ps.executeUpdate();
            return true;
        } catch(Exception ex) {
            System.out.println("❌ Error en NominaDAO.actualizar(): " + ex);
            return false;
        }
    }

    /**
     * DELETE - Elimina registro de nómina
     * 
     * ⚠️ IMPORTANTE: Respetar orden de eliminación por claves foráneas
     * Orden correcto:
     * 1. NominaDAO.eliminar(cedula)  - primero ← Aquí
     * 2. TrabajoDAO.eliminar(cedula) - segundo
     * 3. EmpleadoDAO.eliminar(cedula) - tercero
     * 
     * @param cedula Cédula del empleado
     * @return true si fue exitoso, false si hubo error
     */
    public boolean eliminar(int cedula) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM nomina WHERE cedula=?");
            ps.setInt(1, cedula);
            ps.executeUpdate();
            return true;
        } catch(Exception ex) {
            System.out.println("❌ Error en NominaDAO.eliminar(): " + ex);
            return false;
        }
    }
}
