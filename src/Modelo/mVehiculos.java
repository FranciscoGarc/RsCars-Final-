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

public class mVehiculos {

    public int idCliente;
    public int idVehiculo;
    public String marca;
    public String modelo;
    public String placa;
    public int año;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public boolean AgVe(mVehiculos vehiculosModelo) {

        try {

            PreparedStatement addCliente = conx.getConexion().prepareStatement("INSERT INTO tbVehiculos (marca, modelo, año, placa, idCliente) VALUES  (?, ?, ?, ?, ?);");

            addCliente.setString(1, vehiculosModelo.getMarca());
            addCliente.setString(2, vehiculosModelo.getModelo());
            addCliente.setInt(3, vehiculosModelo.getAño());
            addCliente.setString(4, vehiculosModelo.getPlaca());
            addCliente.setInt(5, vehiculosModelo.getIdCliente());

            addCliente.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean EliminarVeh(mVehiculos vehiculosModelo) {
        try {
            PreparedStatement deleteUser = conx.getConexion().prepareStatement("DELETE FROM tbVehiculos WHERE idVehiculo = ?");
            deleteUser.setInt(1, vehiculosModelo.getIdVehiculo());

            int rowsAffected = deleteUser.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public void mostrarDatosEnTabla(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"Usuario", "Correo", "Nombre", "Apellido", "idVehiculo", "Marca", "Modelo", "Año", "Placa"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT U.usuario, U.correo,C.nombre ,C.apellido,V.idVehiculo,V.marca, V.modelo, V.año, V.placa "
                    + "FROM tbUsuarios U "
                    + "JOIN tbClientes C ON U.idUsuario = C.idUsuario "
                    + "JOIN tbVehiculos V ON C.idCliente = V.idCliente;";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString("usuario"),
                    rs.getString("correo"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("idVehiculo"), rs.getString("marca"), rs.getString("modelo"), rs.getString("año"), rs.getString("placa")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void buscarVehiculo(String filtro, JTable tbDatosCl) {
        DefaultTableModel modelo = (DefaultTableModel) tbDatosCl.getModel();
        modelo.setRowCount(0);  // Limpiar la tabla antes de agregar resultados

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT U.usuario, U.correo, C.nombre, C.apellido, V.idVehiculo, V.marca, V.modelo, V.año, V.placa "
                    + "FROM tbUsuarios U "
                    + "JOIN tbClientes C ON U.idUsuario = C.idUsuario "
                    + "JOIN tbVehiculos V ON C.idCliente = V.idCliente "
                    + "WHERE U.usuario LIKE ? OR V.modelo LIKE ? OR V.marca LIKE ?";

            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + filtro + "%");
            statement.setString(2, "%" + filtro + "%");
            statement.setString(3, "%" + filtro + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("usuario"),
                    rs.getString("correo"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("idVehiculo"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("año"),
                    rs.getString("placa")
                });
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
