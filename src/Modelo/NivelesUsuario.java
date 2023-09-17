/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Forms.RegistroForm;
import java.sql.*;

/**
 *
 * @author Fran
 */
public class NivelesUsuario {

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    private int idTipo;
    private String tipo;
    
        public void traerIdDeTbTipo(mRegistro modeloUsuario, NivelesUsuario modeloNivelesUsuario) {
        try {
            String query = "SELECT idtipo from tbTipoUsuarios  where tipo = ?";
            PreparedStatement addSeccion = conx.getConexion().prepareStatement(query);
            addSeccion.setString(1, modeloNivelesUsuario.getTipo());
            ResultSet resultado = addSeccion.executeQuery();

            while (resultado.next()) {
                modeloUsuario.setIdTipo(resultado.getInt("idTipo"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //Mostrar datos en Combobox
    public void llenarComboBox(RegistroForm vistaRegistro) {
        vistaRegistro.cbLista.removeAllItems();
        try {
            String query = "select tipo from tbTipoUsuarios where idTipo<5;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
              vistaRegistro.cbLista.addItem(resultado.getString("tipo"));   
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
}
