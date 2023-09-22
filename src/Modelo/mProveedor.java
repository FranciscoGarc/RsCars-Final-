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

public class mProveedor {

    public int codigo;
    public String nombre;
    public String correo;
    public String telefono;
    public int idProveedor;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public boolean AgPro(mProveedor proveedorModelo) {

        try {

            PreparedStatement addCliente = conx.getConexion().prepareStatement("INSERT INTO tbProveedores (nombre, teléfono, correo, codigoProv) VALUES  (?, ?, ?, ?);");

            addCliente.setString(1, proveedorModelo.getNombre());
            addCliente.setString(2, proveedorModelo.getTelefono());
            addCliente.setString(3, proveedorModelo.getCorreo());
            addCliente.setInt(4, proveedorModelo.getCodigo());

            addCliente.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean EliminarRep(mProveedor proveedorModelo) {
        try {
            PreparedStatement deleteUser = conx.getConexion().prepareStatement("DELETE FROM tbProveedores WHERE idProveedor = ?");
            deleteUser.setInt(1, proveedorModelo.getIdProveedor());

            int rowsAffected = deleteUser.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public void mostrarProveedoresEnTabla(JTable tbDatosProveedores) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Proveedor", "Nombre", "Teléfono", "Correo", "Código"});
        tbDatosProveedores.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT idProveedor, nombre, teléfono, correo, codigoProv FROM tbProveedores;";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idProveedor"), rs.getString("nombre"), rs.getString("teléfono"), rs.getString("correo"), rs.getInt("codigoProv")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void buscarProveedoresPorNombre(String nombre, JTable tbDatosProveedores) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"ID Proveedor", "Nombre", "Teléfono", "Correo", "Código"});
        tbDatosProveedores.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT idProveedor, nombre, teléfono, correo, codigoProv FROM tbProveedores WHERE nombre LIKE ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + nombre + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idProveedor"), rs.getString("nombre"), rs.getString("teléfono"), rs.getString("correo"), rs.getInt("codigoProv")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
