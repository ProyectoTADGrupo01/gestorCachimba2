/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Mario
 */
public class Accesorio {
    private Integer id;
    private String nombre;
    private Float alquiler;

    public Accesorio(Integer id, String nombre, Float alquiler) {
        this.id = id;
        this.nombre = nombre;
        this.alquiler = alquiler;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Float alquiler) {
        this.alquiler = alquiler;
    }
    
}
