/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.vaadin.ui.Notification;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran13
 */
public class Dao {

    private Connection connection;

    public Dao() {
        this.connection = null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void conexion() throws InstantiationException, IllegalAccessException {
        String login = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/shishas_db";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.setConnection(DriverManager.getConnection(url, login, password));
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void cerrarConexion() throws SQLException {
        this.getConnection().close();
    }

    public Integer login(String dni, String password) throws SQLException {
        Integer resultado = 0;

        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT rol FROM usuarios WHERE dni='"
                + dni + "'" + "AND password='" + password + "'");
        while (resultSet.next()) {
            if (resultSet.getString("id") != null) {
                if (resultSet.getString("id").equals("admin")) {
                    resultado = 1;
                } else if (resultSet.getString("id").equals("usuario")) {
                    resultado = 2;
                }

            }
        }

        return resultado;
    }

    public List<Usuario> getListaUsuarios() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios ORDER BY id");
        while (resultSet.next()) {
            Usuario usuario = new Usuario(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getString("dni"),
                    resultSet.getString("password"),
                    Integer.parseInt(resultSet.getString("edad")),
                    resultSet.getString("rol"));
            listaUsuarios.add(usuario);
        }
        resultSet.close();
        statement.close();
        return listaUsuarios;
    }

    public Usuario getUsuario(Integer id) throws SQLException {
        Usuario usuario = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios WHERE id='" + id + "'");
        while (resultSet.next()) {
            usuario = new Usuario(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getString("dni"),
                    resultSet.getString("password"),
                    Integer.parseInt(resultSet.getString("edad")),
                    resultSet.getString("rol"));
        }

        return usuario;
    }

    public int crearUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre,apellidos,dni,edad,rol) "
                + "VALUES ('" + usuario.getNombre() + "','"
                + usuario.getApellidos() + "','" + usuario.getDni() + "','"
                + usuario.getEdad() + "','" + usuario.getRol() + "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarUsuario(Integer id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarUsuario(Usuario usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre='" + usuario.getNombre()
                + "', apellidos='" + usuario.getApellidos()
                + "', dni='" + usuario.getDni()
                + "', edad=" + usuario.getEdad()
                + ", rol='" + usuario.getRol() + "' WHERE id=" + usuario.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    
    public List<Cachimba> getListaCachimbas() throws SQLException {
        List<Cachimba> listaCachimbas = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cachimbas ORDER BY id");
        while (resultSet.next()) {
            Cachimba cachimba = new Cachimba(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("marca"),
                    resultSet.getString("modelo"),
                    Float.parseFloat(resultSet.getString("alquiler")));
            listaCachimbas.add(cachimba);
        }
        resultSet.close();
        statement.close();
        return listaCachimbas;
    }

    public Cachimba getCachimba(Integer id) throws SQLException {
        Cachimba cachimba = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cachimbas WHERE id='" + id + "'");
        while (resultSet.next()) {
            cachimba = new Cachimba(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("marca"),
                    resultSet.getString("modelo"),
                    Float.parseFloat(resultSet.getString("rol")));
        }

        return cachimba;
    }

    public int crearCachimba(Cachimba cachimba) throws SQLException {
        String query = "INSERT INTO cachimbas (marca,modelo,alquiler) "
                + "VALUES ('" + cachimba.getMarca() + "','"
                + cachimba.getModelo() + "','" + cachimba.getAlquiler() + "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarCachimba(Integer id) throws SQLException {
        String query = "DELETE FROM cachimbas WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarCachimba(Cachimba cachimba) throws SQLException {
        String query = "UPDATE cachimbas SET marca='" + cachimba.getMarca()
                + "', modelo='" + cachimba.getModelo()
                + "', alquiler=" + cachimba.getAlquiler()
                + " WHERE id=" + cachimba.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    
    public List<Accesorio> getListaAccesorios() throws SQLException {
        List<Accesorio> listaAccesorios = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cachimbas ORDER BY id");
        while (resultSet.next()) {
            Accesorio accesorio = new Accesorio(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("nombre"),
                    Float.parseFloat(resultSet.getString("alquiler")));
            listaAccesorios.add(accesorio);
        }
        resultSet.close();
        statement.close();
        return listaAccesorios;
    }
    
    public Accesorio getAccesorio(Integer id) throws SQLException {
        Accesorio accesorio = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM accesorios WHERE id='" + id + "'");
        while (resultSet.next()) {
            accesorio = new Accesorio(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("nombre"),
                    Float.parseFloat(resultSet.getString("alquiler")));
        }

        return accesorio;
    }

    public int crearAccesorio(Accesorio accesorio) throws SQLException {
        String query = "INSERT INTO accesorios (nombre,alquiler) "
                + "VALUES ('" + accesorio.getNombre()+ "','"
                + accesorio.getAlquiler() + "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarAccesorio(Integer id) throws SQLException {
        String query = "DELETE FROM accesorios WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarAccesorio(Accesorio accesorio) throws SQLException {
        String query = "UPDATE accesorios SET nombre='" + accesorio.getNombre()
                + "', alquiler=" + accesorio.getAlquiler()
                + " WHERE id=" + accesorio.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    
    public List<Carbon> getListaCarbones() throws SQLException {
        List<Carbon> listaCarbones = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM carbones ORDER BY id");
        while (resultSet.next()) {
            Carbon carbon = new Carbon(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("tipo"),
                    Float.parseFloat(resultSet.getString("precio")));
            listaCarbones.add(carbon);
        }
        resultSet.close();
        statement.close();
        return listaCarbones;
    }
    
    public Carbon getCarbon(Integer id) throws SQLException {
        Carbon carbon = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM carbones WHERE id='" + id + "'");
        while (resultSet.next()) {
            carbon = new Carbon(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("tipo"),
                    Float.parseFloat(resultSet.getString("precio")));
        }

        return carbon;
    }

    public int crearCarbon(Carbon carbon) throws SQLException {
        String query = "INSERT INTO carbones (tipo,precio) "
                + "VALUES ('" + carbon.getTipo()+ "','"
                + carbon.getPrecio()+ "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarCarbon(Integer id) throws SQLException {
        String query = "DELETE FROM carbones WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarCarbon(Carbon carbon) throws SQLException {
        String query = "UPDATE carbones SET tipo='" + carbon.getTipo()
                + "', precio=" + carbon.getPrecio()
                + " WHERE id=" + carbon.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    
    public List<Cazoleta> getListaCazoletas() throws SQLException {
        List<Cazoleta> listaCazoletas = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cazoletas ORDER BY id");
        while (resultSet.next()) {
            Cazoleta cazoleta = new Cazoleta(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("tipo"),
                    Float.parseFloat(resultSet.getString("precio")));
            listaCazoletas.add(cazoleta);
        }
        resultSet.close();
        statement.close();
        return listaCazoletas;
    }
    
    public Cazoleta getCazoleta(Integer id) throws SQLException {
        Cazoleta cazoleta = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cazoletas WHERE id='" + id + "'");
        while (resultSet.next()) {
            cazoleta = new Cazoleta(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("tipo"),
                    Float.parseFloat(resultSet.getString("precio")));
        }

        return cazoleta;
    }

    public int crearCazoleta(Cazoleta cazoleta) throws SQLException {
        String query = "INSERT INTO cazoletas (tipo,precio) "
                + "VALUES ('" + cazoleta.getTipo()+ "','"
                + cazoleta.getPrecio()+ "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarCazoleta(Integer id) throws SQLException {
        String query = "DELETE FROM cazoletas WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarCazoleta(Cazoleta cazoleta) throws SQLException {
        String query = "UPDATE cazoletas SET tipo='" + cazoleta.getTipo()
                + "', precio=" + cazoleta.getPrecio()
                + " WHERE id=" + cazoleta.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    
    public List<Manguera> getListaMangueras() throws SQLException {
        List<Manguera> listaMangueras = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM mangueras ORDER BY id");
        while (resultSet.next()) {
            Manguera manguera = new Manguera(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("tipo"),
                    Float.parseFloat(resultSet.getString("precio")));
            listaMangueras.add(manguera);
        }
        resultSet.close();
        statement.close();
        return listaMangueras;
    }
    
    public Manguera getManguera(Integer id) throws SQLException {
        Manguera manguera = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM mangueras WHERE id='" + id + "'");
        while (resultSet.next()) {
            manguera = new Manguera(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("tipo"),
                    Float.parseFloat(resultSet.getString("precio")));
        }

        return manguera;
    }

    public int crearManguera(Manguera manguera) throws SQLException {
        String query = "INSERT INTO mangueras (tipo,precio) "
                + "VALUES ('" + manguera.getTipo()+ "','"
                + manguera.getPrecio()+ "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarManguera(Integer id) throws SQLException {
        String query = "DELETE FROM mangueras WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarManguera(Manguera manguera) throws SQLException {
        String query = "UPDATE mangueras SET tipo='" + manguera.getTipo()
                + "', precio=" + manguera.getPrecio()
                + " WHERE id=" + manguera.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    
    public List<Tabaco> getListaTabacos() throws SQLException {
        List<Tabaco> listaTabacos = new ArrayList();
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM tabacos ORDER BY id");
        while (resultSet.next()) {
            Tabaco tabaco = new Tabaco(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("sabor"),
                    Float.parseFloat(resultSet.getString("alquiler")));
            listaTabacos.add(tabaco);
        }
        resultSet.close();
        statement.close();
        return listaTabacos;
    }
    
    public Tabaco getTabaco(Integer id) throws SQLException {
        Tabaco tabaco = null;
        Statement statement = this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM tabacos WHERE id='" + id + "'");
        while (resultSet.next()) {
            tabaco = new Tabaco(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("sabor"),
                    Float.parseFloat(resultSet.getString("alquiler")));
        }

        return tabaco;
    }

    public int crearTabaco(Tabaco tabaco) throws SQLException {
        String query = "INSERT INTO tabacos (sabor,alquiler) "
                + "VALUES ('" + tabaco.getSabor()+ "','"
                + tabaco.getAlquiler()+ "')";

        PreparedStatement preparedStatement
                = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        Notification.show(query, Notification.Type.ERROR_MESSAGE);

        return resultado;

    }

    public int eliminarTabaco(Integer id) throws SQLException {
        String query = "DELETE FROM tabacos WHERE id='" + id + "'";
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }

    public int editarTabaco(Tabaco tabaco) throws SQLException {
        String query = "UPDATE tabacos SET sabor='" + tabaco.getSabor()
                + "', alquiler=" + tabaco.getAlquiler()
                + " WHERE id=" + tabaco.getId();
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
        int resultado = preparedStatement.executeUpdate();

        return resultado;
    }
    

}
