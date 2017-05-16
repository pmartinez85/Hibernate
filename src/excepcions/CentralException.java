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
public class CentralException extends Exception{
    
     @Override
    public String getMessage() {
        return "La central ja està assignada a una Empresa.";
    }
    
}
