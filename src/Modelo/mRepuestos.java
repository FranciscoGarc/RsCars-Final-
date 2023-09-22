/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.conx;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class mRepuestos {

    public int idRepuesto;
    public String descripcion;
    public BigDecimal precio;
    public int stock;
    public int idProveedor;

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public boolean AgRe(mRepuestos repuestosModelo) {

        try {

            PreparedStatement addCliente = conx.getConexion().prepareStatement("INSERT INTO tbRepuestos (descripción, precio, stock, idProveedor) VALUES  (?, ?, ?, ?);");

            addCliente.setString(1, repuestosModelo.getDescripcion());
            addCliente.setBigDecimal(2, repuestosModelo.getPrecio());
            addCliente.setInt(3, repuestosModelo.getStock());
            addCliente.setInt(4, repuestosModelo.getIdProveedor());

            addCliente.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean EliminarRep(mRepuestos repuestosModelo) {
        try {
            PreparedStatement deleteUser = conx.getConexion().prepareStatement("DELETE FROM tbRepuestos WHERE idRepuesto = ?");
            deleteUser.setInt(1, repuestosModelo.getIdRepuesto());

            int rowsAffected = deleteUser.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public void mostrarRepuestosEnTabla(JTable tbDatosRepuestos) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Repuesto", "Descripción", "Precio", "Stock", "Proveedor"});
        tbDatosRepuestos.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT R.idRepuesto, R.descripción, R.precio, R.stock, P.nombre FROm tbRepuestos R INNER JOIN tbProveedores P ON R.idProveedor = P.idProveedor;";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idRepuesto"), rs.getString("descripción"), rs.getDouble("precio"), rs.getInt("stock"), rs.getString("nombre")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void buscarRepuestos(String filtro, JTable tbDatosRepuestos) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Repuesto", "Descripción", "Precio", "Stock", "Proveedor"});
        tbDatosRepuestos.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT R.idRepuesto, R.descripción, R.precio, R.stock, P.nombre FROm tbRepuestos R INNER JOIN tbProveedores P ON R.idProveedor = P.idProveedor Where P.nombre LIKE ? OR R.descripción LIKE ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + filtro + "%");
            statement.setString(2, "%" + filtro + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idRepuesto"), rs.getString("descripción"), rs.getDouble("precio"), rs.getInt("stock"), rs.getString("nombre")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
