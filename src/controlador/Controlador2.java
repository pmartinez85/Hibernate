/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entitats.Empresa;
import entitats.Seu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author pedro
 */
public class Controlador2 {
    
    ActionListener actionListener = new ActionListener() {
            int filasel = v.getEmpresataula().getSelectedRow();
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                
                if (actionEvent.getSource().equals(v.getMostrarbtn())) {

                }
                if (actionEvent.getSource().equals(v.getEsborrarbtn())) {
                    if (filasel!=-1){
                            m.getEmpreses().elimina(empresa);
                            borrarCamps();
                            carregaTaula ((ArrayList) m.getEmpreses().obtenLlista(), v.getEmpresataula(), Empresa.class);        
                    }
                    else JOptionPane.showMessageDialog(null, "Per borrar una empresa primer l'has de seleccionar!!", "Error", JOptionPane.ERROR_MESSAGE);                
                }
                 if (actionEvent.getSource().equals(v.getModificarbtn())) {
                     if (filasel!=-1 && (!nomempresa.equals("") || cif!=0)){
                            
                                    m.getEmpreses().obte(cif);
                                    borrarCamps();
                                    carregaTaula ((ArrayList) m.getEmpreses().obtenLlista(), v.getEmpresataula(), Empresa.class);
                                
                            }
                            else JOptionPane.showMessageDialog(null, "Per modificar una empresa primer l'has de seleccionar i posar algun valor!!", "Error", JOptionPane.ERROR_MESSAGE);                
                        }
                if (actionEvent.getSource().equals(v.getInsertarbtn())) {
                    empresa = new Empresa();
                         
                    empresa.set2_nomempresa(v.getNomjtxf().getText());
                            
                    empresa.set3_cif(Integer.parseInt(v.getCifjtxf().getText()));
                      
                    empresa.set4_seus((List<Seu>) (Seu) v.getSeusCB().getSelectedItem());
                    
                    Seu seu = (Seu) empresa.get4_seus();
                    
                    try{
                        seu.set5_central(empresa);
                        m.getEmpreses().guarda(empresa);
                        m.getSeus().actualitza(seu);
                        m.getEmpreses().guarda(empresa);
                    
                    }catch(NullPointerException e ) {
                        System.out.println("No has triat una Seu per la teva empresa");
                        JOptionPane.showMessageDialog(null,"Crea una Seu per la teva Empresa."); 
                    }
                   
                    
                    
                    carregaTaula ((ArrayList) m.getEmpreses().obtenLlista(), v.getEmpresataula(), Empresa.class);
                    
                    
                
                }
                if (actionEvent.getSource().equals(v.getSortirbtn())) {
                   // EmpresaDAO.tancaSessio();

                }
            }

        };
        
        v.getEsborrarbtn().addActionListener(actionListener);
        v.getInsertarbtn().addActionListener(actionListener);
        v.getModificarbtn().addActionListener(actionListener);
        v.getMostrarbtn().addActionListener(actionListener);
        v.getSortirbtn().addActionListener(actionListener);
    
}
