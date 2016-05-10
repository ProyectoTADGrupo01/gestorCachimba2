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

}
