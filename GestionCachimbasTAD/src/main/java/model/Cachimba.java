/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Fran13
 */
public class Cachimba {
    
    private Integer id;
    private String marca;
    private String modelo;
    private float alquiler;
    
    
    public Cachimba(Integer id, String marca,String modelo, float alquiler){
        this.id = id;
        this.marca=marca;
        this.modelo=modelo;
        this.alquiler=alquiler;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setMarca(String marca){
        this.marca=marca;
    }
    public void setModelo(String modelo){
        this.modelo=modelo;
    }
    public void setAlquiler(float alquiler){
        this.alquiler=alquiler;
    }
    
    public String getMarca(){
        return this.marca;
    }

    public String getModelo() {
        return modelo;
    }

    public float getAlquiler() {
        return alquiler;
    }
    
    
    
    
    
}
