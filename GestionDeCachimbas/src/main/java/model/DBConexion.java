/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Fran13
 */
public class DBConexion {

    /**
     * Parametros de conexion mario sanchez (mariofenix2@gmail.com) Host:
     * mysql.hostinger.es
     *
     * Nombre BD: u940594159_db
     *
     * Usuario: u940594159_root
     *
     * Password: 8SLHCltgBF
     */
//    static String bd = "u940594159_db";
//    static String login = "u940594159_root";
//    static String password = "8SLHCltgBF";
//    static String url = "jdbc:mysql://mysql.hostinger.es/" + bd;
    
    static String bd = "shishas_db";
    static String login = "root";
    static String password = "";
    static String url = "jdbc:mysql://localhost/" + bd;

    Connection connection = null;

    /**
     * Constructor de DbConnection
     */
    public DBConexion() {
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            connection = DriverManager.getConnection(url, login, password);

            if (connection != null) {
                System.out.println("Conexión a base de datos " + bd + " OK\n");
            }
        } catch (SQLException e) {

            System.out.println(e);

        } catch (ClassNotFoundException e) {

            System.out.println(e);

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    /**
     * Permite retornar la conexión
     */
    public Connection getConnection() {
        return connection;
    }

    public void desconectar() {
        connection = null;
    }
}
