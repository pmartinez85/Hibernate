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
public class EsCentralException extends Exception{
    
    @Override
    public String getMessage() {
        return "La seu central no es pot eliminar, treu l'assignació.";
    }
    
}
