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
public class mUsuario {
    
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
   
        public boolean AgregarUsuario(mUsuario usuarioModelo) {
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
    
        public boolean ActualizarUsuario(mUsuario usuarioModelo) {
        try {
            PreparedStatement updateUsuario = conx.getConexion().prepareStatement("UPDATE tbUsuarios SET idTipo = ?, usuario = ?, contra = ?, correo = ? WHERE idUsuario = ?");
            updateUsuario.setInt(1, usuarioModelo.getIdTipo());
            updateUsuario.setString(2, usuarioModelo.getUsuario());
            updateUsuario.setString(3, usuarioModelo.getContra());
            updateUsuario.setString(4, usuarioModelo.getCorreo());
            updateUsuario.setInt(5, usuarioModelo.getIdUsuario());

            updateUsuario.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
        
        public boolean EliminarUsuario(mUsuario usuarioModelo) {
        try {
            PreparedStatement deleteUser = conx.getConexion().prepareStatement("DELETE FROM tbUsuarios WHERE idUsuario = ?");
            deleteUser.setInt(1, usuarioModelo.getIdUsuario());

            int rowsAffected = deleteUser.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
        
public ArrayList<String> obtenerTiposUsuarios() {
    ArrayList<String> tiposUsuarios = new ArrayList<>();

    try {
        java.sql.Connection conexion = conx.getConexion();
        Statement statement = conexion.createStatement();
        ResultSet rs = statement.executeQuery("SELECT tipo FROM tbTipoUsuarios");

        while (rs.next()) {
            tiposUsuarios.add(rs.getString("tipo"));
        }

        statement.close(); // Cerrar el Statement
        conexion.close(); // Cerrar la conexión
    } catch (SQLException ex) {
        System.out.println(ex.toString());
    }

    return tiposUsuarios;
}
}
