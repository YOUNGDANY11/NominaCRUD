package com.mycompany.nominaa.vista;

import com.mycompany.nominaa.dao.EmpleadoDAO;
import com.mycompany.nominaa.modelo.Empleado;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventana extends JFrame {

    JTextField txtCedula = new JTextField();
    JTextField txtNombre = new JTextField();
    JTextField txtHoras = new JTextField();
    JTextField txtValorHora = new JTextField();

    JButton btnGuardar = new JButton("Guardar");
    JButton btnMostrar = new JButton("Mostrar");
    JButton btnBuscar = new JButton("Buscar");
    JButton btnActualizar = new JButton("Actualizar");
    JButton btnEliminar = new JButton("Eliminar");

    JTable tabla;
    DefaultTableModel modelo;

    EmpleadoDAO dao = new EmpleadoDAO();

    public Ventana() {

        setTitle("Sistema de Nómina");
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // PANEL FORMULARIO
        JPanel panelForm = new JPanel(new GridLayout(4,2,10,10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));

        panelForm.add(new JLabel("Cédula:"));
        panelForm.add(txtCedula);

        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Horas trabajadas:"));
        panelForm.add(txtHoras);

        panelForm.add(new JLabel("Valor hora:"));
        panelForm.add(txtValorHora);

        // PANEL BOTONES
        JPanel panelBotones = new JPanel();

        panelBotones.add(btnGuardar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // TABLA
        modelo = new DefaultTableModel();
        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Horas");
        modelo.addColumn("Salario");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // ORGANIZACIÓN CORRECTA
        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // BOTÓN GUARDAR
        btnGuardar.addActionListener(e -> {

            try{

                int cedula = Integer.parseInt(txtCedula.getText());
                String nombre = txtNombre.getText();
                int horas = Integer.parseInt(txtHoras.getText());
                double valor = Double.parseDouble(txtValorHora.getText());

                Empleado emp = new Empleado(cedula,nombre,horas,valor);

                if(dao.insertar(emp)){
                    JOptionPane.showMessageDialog(this,"Empleado guardado");
                    limpiar();
                    cargarTabla(dao.listar());
                }else{
                    JOptionPane.showMessageDialog(this,"Ya existe un empleado con esa cédula");
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Datos inválidos");
            }

        });

        // MOSTRAR
        btnMostrar.addActionListener(e -> cargarTabla(dao.listar()));

        // BUSCAR
        btnBuscar.addActionListener(e -> {

            List<String> lista = dao.buscarPorNombre(txtNombre.getText());

            if(lista.isEmpty()){
                JOptionPane.showMessageDialog(this,"Empleado no encontrado");
            }else{
                cargarTabla(lista);
            }

        });

        // ACTUALIZAR
        btnActualizar.addActionListener(e -> {

            try{

                int cedula = Integer.parseInt(txtCedula.getText());
                String nombre = txtNombre.getText();
                int horas = Integer.parseInt(txtHoras.getText());
                double valor = Double.parseDouble(txtValorHora.getText());

                Empleado emp = new Empleado(cedula,nombre,horas,valor);

                if(dao.actualizar(emp)){
                    JOptionPane.showMessageDialog(this,"Empleado actualizado");
                    cargarTabla(dao.listar());
                }else{
                    JOptionPane.showMessageDialog(this,"Empleado no existe");
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Datos inválidos");
            }

        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {

            try{

                int cedula = Integer.parseInt(txtCedula.getText());

                if(dao.eliminar(cedula)){
                    JOptionPane.showMessageDialog(this,"Empleado eliminado");
                    cargarTabla(dao.listar());
                }else{
                    JOptionPane.showMessageDialog(this,"Empleado no existe");
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Ingrese una cédula válida");
            }

        });

        setVisible(true);
    }

    // CARGAR TABLA
    private void cargarTabla(List<String> lista){

        modelo.setRowCount(0);

        for(String s : lista){

            String[] datos = s.split("\\|");

            modelo.addRow(new Object[]{
                datos[0].trim(),
                datos[1].trim(),
                datos[2].trim(),
                datos[3].trim()
            });

        }

    }

    // LIMPIAR CAMPOS
    private void limpiar(){

        txtCedula.setText("");
        txtNombre.setText("");
        txtHoras.setText("");
        txtValorHora.setText("");

    }
}