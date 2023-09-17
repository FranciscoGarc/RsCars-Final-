/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Fran
 */
public class MostrarDatosTabla {

    public void mostrarDatosEnTabla(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"idUsuario", "Usuario", "Contraseña", "Correo", "idCliente", "Nombre", "Apellido", "Teléfono", "Dirección", "DUI"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario,u.usuario,u.contra,u.correo,c.idCliente,c.nombre,c.apellido,c.telefono,c.direccion,c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbClientes c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 5";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idUsuario"), rs.getString("usuario"), rs.getString("contra"),
                    rs.getString("correo"), rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("telefono"), rs.getString("direccion"), rs.getString("dui")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void buscarDatosEnTabla(JTable tbDatosCl, String textoBusqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tbDatosCl.getModel();
        modelo.setRowCount(0); // Limpiamos los datos actuales en la tabla

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario, u.usuario, u.contra, u.correo, c.idCliente, c.nombre, c.apellido, c.telefono, c.direccion, c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbClientes c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 5 AND (c.nombre LIKE ? OR c.apellido LIKE ? OR u.usuario LIKE ?)";

            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + textoBusqueda + "%");
            statement.setString(2, "%" + textoBusqueda + "%");
            statement.setString(3, "%" + textoBusqueda + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idUsuario"),
                    rs.getString("usuario"),
                    rs.getString("contra"),
                    rs.getString("correo"),
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("dui")
                });
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private void ocultarColumna(JTable table, String columnName) {
        TableColumnModel tcm = table.getColumnModel();
        int columnIndex = -1;
        for (int i = 0; i < tcm.getColumnCount(); i++) {
            if (tcm.getColumn(i).getHeaderValue().equals(columnName)) {
                columnIndex = i;
                break;
            }
        }
        if (columnIndex != -1) {
            tcm.removeColumn(tcm.getColumn(columnIndex));
        }
    }

    public void mostrarDatosEnTablaMecanicos(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"idUsuario", "Usuario", "Contraseña", "Correo", "idMecanico", "Nombre", "Apellido", "Teléfono", "Dirección", "DUI"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario,u.usuario,u.contra,u.correo,c.idMecanico,c.nombre,c.apellido,c.telefono,c.direccion,c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbMecanicos c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 2";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idUsuario"), rs.getString("usuario"), rs.getString("contra"),
                    rs.getString("correo"), rs.getInt("idMecanico"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("telefono"), rs.getString("direccion"), rs.getString("dui")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void buscarDatosEnTablaMecanicos(JTable tbDatosCl, String textoBusqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tbDatosCl.getModel();
        modelo.setRowCount(0); // Limpiamos los datos actuales en la tabla

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario, u.usuario, u.contra, u.correo, c.idMecanico, c.nombre, c.apellido, c.telefono, c.direccion, c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbMecanicos c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 2 AND (c.nombre LIKE ? OR c.apellido LIKE ? OR u.usuario LIKE ?)";

            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + textoBusqueda + "%");
            statement.setString(2, "%" + textoBusqueda + "%");
            statement.setString(3, "%" + textoBusqueda + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idUsuario"),
                    rs.getString("usuario"),
                    rs.getString("contra"),
                    rs.getString("correo"),
                    rs.getInt("idMecanico"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("dui")
                });
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void mostrarDatosEnTablaContadores(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"idUsuario", "Usuario", "Contraseña", "Correo", "idEmpleado", "Nombre", "Apellido", "Teléfono", "Dirección", "DUI"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario,u.usuario,u.contra,u.correo,c.idEmpleado,c.nombre,c.apellido,c.telefono,c.direccion,c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbContadores c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 3";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idUsuario"), rs.getString("usuario"), rs.getString("contra"),
                    rs.getString("correo"), rs.getInt("idEmpleado"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("telefono"), rs.getString("direccion"), rs.getString("dui")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void buscarDatosEnTablaContadores(JTable tbDatosCl, String textoBusqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tbDatosCl.getModel();
        modelo.setRowCount(0); // Limpiamos los datos actuales en la tabla

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario, u.usuario, u.contra, u.correo, c.idEmpleado, c.nombre, c.apellido, c.telefono, c.direccion, c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbContadores c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 3 AND (c.nombre LIKE ? OR c.apellido LIKE ? OR u.usuario LIKE ?)";

            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + textoBusqueda + "%");
            statement.setString(2, "%" + textoBusqueda + "%");
            statement.setString(3, "%" + textoBusqueda + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idUsuario"),
                    rs.getString("usuario"),
                    rs.getString("contra"),
                    rs.getString("correo"),
                    rs.getInt("idEmpleado"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("dui")
                });
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void mostrarDatosEnTablaRecep(JTable tbDatosCl) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que todas las celdas no sean editables
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"idUsuario", "Usuario", "Contraseña", "Correo", "idRecepcionista", "Nombre", "Apellido", "Teléfono", "Dirección", "DUI"});
        tbDatosCl.setModel(modelo);

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario,u.usuario,u.contra,u.correo,c.idRecepcionista,c.nombre,c.apellido,c.telefono,c.direccion,c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbRecepcionistas c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 4";

            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("idUsuario"), rs.getString("usuario"), rs.getString("contra"),
                    rs.getString("correo"), rs.getInt("idRecepcionista"), rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("telefono"), rs.getString("direccion"), rs.getString("dui")});
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void buscarDatosEnTablaRecep(JTable tbDatosCl, String textoBusqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tbDatosCl.getModel();
        modelo.setRowCount(0); // Limpiamos los datos actuales en la tabla

        try {
            Connection conexion = conx.getConexion();
            String sql = "SELECT u.idUsuario, u.usuario, u.contra, u.correo, c.idRecepcionista, c.nombre, c.apellido, c.telefono, c.direccion, c.dui "
                    + "FROM tbUsuarios u "
                    + "JOIN tbTipoUsuarios tu ON u.idTipo = tu.idTipo "
                    + "LEFT JOIN tbRecepcionistas c ON u.idUsuario = c.idUsuario "
                    + "WHERE tu.idTipo = 4 AND (c.nombre LIKE ? OR c.apellido LIKE ? OR u.usuario LIKE ?)";

            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, "%" + textoBusqueda + "%");
            statement.setString(2, "%" + textoBusqueda + "%");
            statement.setString(3, "%" + textoBusqueda + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idUsuario"),
                    rs.getString("usuario"),
                    rs.getString("contra"),
                    rs.getString("correo"),
                    rs.getInt("idRecepcionista"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("dui")
                });
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
