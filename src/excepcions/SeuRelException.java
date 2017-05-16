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
public class SeuRelException extends Exception{
    @Override
    public String getMessage() {
        return "La Seu ja està assignada a una Empresa. Treu la relació abans.";
    }
    
}
