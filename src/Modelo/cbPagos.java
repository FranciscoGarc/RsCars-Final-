/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Fran
 */
import Vista.pnlPagos;
import java.sql.*;

public class cbPagos {

    private int idMetodo;
    private String Metodo;

    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public String getMetodo() {
        return Metodo;
    }

    public void setMetodo(String Metodo) {
        this.Metodo = Metodo;
    }
    
        public void traerIdDeTbMetodos(mPagos modeloPagos, cbPagos modeloCbPagos) {
        try {
            String query = "SELECT idFormaPago from tbFormasPago where descripcion = ?";
            PreparedStatement addSeccion = conx.getConexion().prepareStatement(query);
            addSeccion.setString(1, modeloCbPagos.getMetodo());
            ResultSet resultado = addSeccion.executeQuery();

            while (resultado.next()) {
                modeloPagos.setIdMetodo(resultado.getInt("idFormaPago"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
        
            public void llenarComboBoxFor(pnlPagos vistaPagos) {
        vistaPagos.cbIdMetodo.removeAllItems();
        try {
            String query = "select descripcion from tbFormasPago;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaPagos.cbIdMetodo.addItem(resultado.getString("descripcion"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
