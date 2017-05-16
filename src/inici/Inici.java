/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inici;

import controlador.Controlador;
import model.Model;
import vista.Vista;

/**
 *
 * @author pedro
 */
public class Inici {
    
    private static final Model m =new Model();
    private static final Vista v = new Vista();
    
    
    public static void main(String[] args) {
      
        new Controlador(m, v);
        v.setVisible(true);

    }
    
}
