/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran13
 */
public class Dao {

    public Dao() {

    }

    public ResultSet query(String query) {

        DBConexion conx = new DBConexion();
        ResultSet ret = null;

        try {
            Statement st = (Statement) conx.getConnection().createStatement();
            ret = st.executeQuery(query);
            ret.close();
            st.close();
            conx.desconectar();

        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return ret;
        
    }
    
    public List<Usuario> getListaUsuarios () {
        List<Usuario> usuarios = (List<Usuario>) query("SELECT * FROM usuarios");
        return usuarios;
    } 
    
    public Usuario getUsuario (Integer id) {
        Usuario usuario = (Usuario) query("SELECT * FROM usuarios WHERE id=" + id);
        return usuario;
    }
    
    public void crearUsuario (Usuario usuario) {
        query("INSERT INTO usuarios (nombre,apellidos,dni,edad,rol) "
                + "VALUES (" + usuario.getNombre() + ","
                + usuario.getApellidos() + "," + usuario.getDni()+ "," + 
                usuario.getEdad()+ "," + usuario.getRol()+ "," + ")");
    }
    
    public void eliminarUsuario (Integer id) {
        query("DELETE FROM usuarios WHERE id=" + id);
    }
    
    public void editarUsuario (Usuario usuario) {
        query("UPDATE usuarios SET nombre=" + usuario.getNombre() + ", apellidos=" +
                usuario.getApellidos() + ", dni=" + usuario.getDni() + ", edad=" +
                usuario.getEdad() + ", rol=" + usuario.getRol());
    }
    
}
