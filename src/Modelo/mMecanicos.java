/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Fran
 */
public class mMecanicos {

    public int idMecanico;
    public int idUsuario;
    public String nombre ;
    public String apellido;
    public String dui;
    public String nacimiento;
    public String sexo;
    public String direccion;
    public String telefono;
    private JTable ta;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
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

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
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

    public JTable getTa() {
        return ta;
    }

    public void setTa(JTable ta) {
        this.ta = ta;
    }
    
        public boolean AgregarMecanico(mMecanicos modeloMecanicos) {

               try {
                   
                   PreparedStatement addCliente = conx.getConexion().prepareStatement("INSERT INTO tbMecanicos (idUsuario, nombre, apellido, telefono, direccion, dui) VALUES (?, ?, ?, ?, ?, ?);");
                   
                   addCliente.setInt(1, modeloMecanicos.getIdUsuario());
                   addCliente.setString(2, modeloMecanicos.getNombre());
                   addCliente.setString(3, modeloMecanicos.getApellido());
                   addCliente.setString(4, modeloMecanicos.getTelefono());
                   addCliente.setString(5, modeloMecanicos.getDireccion());
                   addCliente.setString(6, modeloMecanicos.getDui());
                   
                   addCliente.executeUpdate();
                   return true;
               } catch (SQLException ex) {
                   System.out.println(ex.toString());
                   return false;
               }
    }

        public boolean ActualizarMecanico(mMecanicos modeloMecanicos) {
        try {
            PreparedStatement updateUsuario = conx.getConexion().prepareStatement("UPDATE tbMecanicos SET nombre = ?, apellido = ?, telefono = ?, direccion = ?, dui = ? WHERE idMecanico = ?");
            updateUsuario.setString(1, modeloMecanicos.getNombre());
            updateUsuario.setString(2, modeloMecanicos.getApellido());
            updateUsuario.setString(3, modeloMecanicos.getTelefono());
            updateUsuario.setString(4, modeloMecanicos.getDireccion());
            updateUsuario.setString(5, modeloMecanicos.getDui());
            updateUsuario.setInt(6, modeloMecanicos.getIdMecanico());

            updateUsuario.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        }

    public boolean EliminarMecanico(mMecanicos modeloMecanicos) {
        try {
            PreparedStatement deleteUser = conx.getConexion().prepareStatement("DELETE FROM tbMecanicos WHERE idMecanico = ?");
            deleteUser.setInt(1, modeloMecanicos.getIdMecanico());

            int rowsAffected = deleteUser.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
}
