/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import Modelo.mUser;

/**
 *
 * @author Fran
 */
public class cUser {
    public int idtipoUser;
    public String nivel;
    public int idUser;
    public int idTipoRegistro;
    public String usuario;
    public String contra;
    public String correo;

    public int getIdtipoUser() {
        return idtipoUser;
    }

    public void setIdtipoUser(int idtipoUser) {
        this.idtipoUser = idtipoUser;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdTipoRegistro() {
        return idTipoRegistro;
    }

    public void setIdTipoRegistro(int idTipoRegistro) {
        this.idTipoRegistro = idTipoRegistro;
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
    
    mUser mdUser = new mUser();
    
    public ResultSet VerificarUsuario()throws SQLException {
        return mdUser.VerificarUsuario(usuario);
    }
    
    public ResultSet Login()throws SQLException {
        return mdUser.SelecionarUser(usuario, contra);
    }
    
    public ResultSet SelecionarNivel()throws SQLException {
        return mdUser.SelecionarNivel(idUser, idtipoUser);
    }
    
}
