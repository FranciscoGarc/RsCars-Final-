/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.conx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fran
 */
public class mContadores {
    public int idContador;
    public int idUsuario;
    public String nombre ;
    public String apellido;
    public String dui;
    public String nacimiento;
    public String sexo;
    public String direccion;
    private JTable ta;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String telefono;

    public int getIdContador() {
        return idContador;
    }

    public void setIdContador(int idContador) {
        this.idContador = idContador;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }
    
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    public boolean AgregarCliente(mContadores contModelo) {

               try {
                   
                   PreparedStatement addCliente = conx.getConexion().prepareStatement("INSERT INTO tbContadores (idUsuario, nombre, apellido, telefono, direccion, dui) VALUES (?, ?, ?, ?, ?, ?);");
                   
                   addCliente.setInt(1, contModelo.getIdUsuario());
                   addCliente.setString(2, contModelo.getNombre());
                   addCliente.setString(3, contModelo.getApellido());
                   addCliente.setString(4, contModelo.getTelefono());
                   addCliente.setString(5, contModelo.getDireccion());
                   addCliente.setString(6, contModelo.getDui());
                   
                   addCliente.executeUpdate();
                   return true;
               } catch (SQLException ex) {
                   System.out.println(ex.toString());
                   return false;
               }
    }

        public boolean ActualizarCliente(mContadores contModelo) {
        try {
            PreparedStatement updateUsuario = conx.getConexion().prepareStatement("UPDATE tbContadores SET nombre = ?, apellido = ?, telefono = ?, direccion = ?, dui = ? WHERE idEmpleado = ?");
            updateUsuario.setString(1, contModelo.getNombre());
            updateUsuario.setString(2, contModelo.getApellido());
            updateUsuario.setString(3, contModelo.getTelefono());
            updateUsuario.setString(4, contModelo.getDireccion());
            updateUsuario.setString(5, contModelo.getDui());
            updateUsuario.setInt(6, contModelo.getIdContador());

            updateUsuario.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        }

    public boolean EliminarCliente(mContadores contModelo) {
        try {
            PreparedStatement deleteUser = conx.getConexion().prepareStatement("DELETE FROM tbContadores WHERE idEmpleado = ?");
            deleteUser.setInt(1, contModelo.getIdUsuario());

            int rowsAffected = deleteUser.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
