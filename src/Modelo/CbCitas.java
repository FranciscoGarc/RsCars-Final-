package Modelo;

import Vista.pnlCitas;
import java.sql.*;

public class CbCitas {

    private int idRepuesto;
    private int idEstado;
    private int idServicio;
    private String Repuesto;
    private String Estado;
    private String Servicio;

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getRepuesto() {
        return Repuesto;
    }

    public void setRepuesto(String Repuesto) {
        this.Repuesto = Repuesto;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String Servicio) {
        this.Servicio = Servicio;
    }

    public void traerIdDeTbEstado(mCitas modeloCitas, CbCitas modeloCbCitas) {
        try {
            String query = "SELECT idEstado from tbEstadoCita  where estado = ?";
            PreparedStatement addSeccion = conx.getConexion().prepareStatement(query);
            addSeccion.setString(1, modeloCbCitas.getEstado());
            ResultSet resultado = addSeccion.executeQuery();

            while (resultado.next()) {
                modeloCitas.setIdEstado(resultado.getInt("idEstado"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void traerIdDeTbRepuesto(mCitas modeloCitas, CbCitas modeloCbCitas) {
        try {
            String query = "SELECT idRepuesto from tbRepuestos where descripción = ?";
            PreparedStatement addSeccion = conx.getConexion().prepareStatement(query);
            addSeccion.setString(1, modeloCbCitas.getRepuesto());
            ResultSet resultado = addSeccion.executeQuery();

            while (resultado.next()) {
                modeloCitas.setIdRepuesto(resultado.getInt("idRepuesto"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void traerIdDeTbServicio(mCitas modeloCitas, CbCitas modeloCbCitas) {
        try {
            String query = "SELECT idServicio from tbServicios where descripcion = ?";
            PreparedStatement addSeccion = conx.getConexion().prepareStatement(query);
            addSeccion.setString(1, modeloCbCitas.getServicio());
            ResultSet resultado = addSeccion.executeQuery();

            while (resultado.next()) {
                modeloCitas.setIdServicio(resultado.getInt("idServicio"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //Mostrar datos en Combobox
    public void llenarComboBoxEs(pnlCitas vistaCitas) {
        vistaCitas.Estado.removeAllItems();
        try {
            String query = "select estado from tbEstadoCita;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaCitas.Estado.addItem(resultado.getString("estado"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //Mostrar datos en Combobox
    public void llenarComboBoxSer(pnlCitas vistaCitas) {
        vistaCitas.cbServicio.removeAllItems();
        try {
            String query = "select descripcion from tbServicios;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaCitas.cbServicio.addItem(resultado.getString("descripcion"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    //Mostrar datos en Combobox

    public void llenarComboBoxRe(pnlCitas vistaCitas) {
        vistaCitas.Repuesto.removeAllItems();
        try {
            String query = "select descripción from tbRepuestos;";

            Statement statement = conx.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);

            while (resultado.next()) {
                vistaCitas.Repuesto.addItem(resultado.getString("descripción"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
