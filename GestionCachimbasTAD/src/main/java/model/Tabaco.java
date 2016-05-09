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
public class Tabaco {
    private String sabor;
    private float alquiler;

    public Tabaco(String sabor, float alquiler) {
        this.sabor = sabor;
        this.alquiler = alquiler;
    }
    
    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public float getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(float alquiler) {
        this.alquiler = alquiler;
    }
    
 
}
