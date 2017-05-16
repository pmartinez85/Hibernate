/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepcions;

/**
 *
 * @author pedro
 */
public class EmpresaRelExeption extends Exception {
    
    @Override
    public String getMessage() {
        return "La Seu està assignada a una Empresa. Tre primer la relació.";
    }
    
}
