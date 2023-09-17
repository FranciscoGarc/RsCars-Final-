/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Modelo.conx;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fran
 */
public class mRegistro {
    
        private int idUsuario;
    private int idTipo;
    private String usuario;
    private String contra;
    private String correo;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
   
        public boolean AgregarUsuarioR(mRegistro usuarioModelo, NivelesUsuario modNivelesUsuario) {
        try {
            PreparedStatement addUsuario = conx.getConexion().prepareStatement("INSERT INTO tbUsuarios(idTipo, usuario, contra, correo) VALUES (?, ?, ?, ?)");
            addUsuario.setInt(1, usuarioModelo.getIdTipo());
            addUsuario.setString(2, usuarioModelo.getUsuario());
            addUsuario.setString(3, usuarioModelo.getContra());
            addUsuario.setString(4, usuarioModelo.getCorreo());

            addUsuario.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
}
