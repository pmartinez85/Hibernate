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
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import model.Model;
import vista.Vista;

/**
 *
 * @author pedro
 */
public class Controlador {

    public Model m;
    private Vista v;
    private int filasel1 = -1;
    private int filasel2 = -1;
    
    Empresa empresa;
    int idempresa;
    String nomempresa;
    int cif ;
    String[] Seus ;
    
    Seu seu;
    int idseu;
    String nomseu;
    String ciutat;
    int telefon;
   
    


    public Controlador(Model m, Vista v) {
        this.m = m;
        this.v = v;
        carregaTaula (m.getEmpreses(), v.getEmpresataula(), Empresa.class);
        carregaTaula (m.getSeus(), v.getSeutaula(), Seu.class);
        v.setVisible(true);
        control();
    }

    public void borrarCamps() {
        v.getNomjtxf().setText("");
        v.getCifjtxf().setText("");
        v.getSeusjtxf().setText("");
    }

 public TableColumn carregaTaula(ArrayList resultSet, JTable taula, Class<?> classe) {
        if (classe == Empresa.class) {
            filasel1 = -1;
        }
        if (classe == Seu.class) {
            filasel2 = -1;
        }
        Vector columnNames = new Vector();
        Vector data = new Vector();
        DefaultTableModel model;
        
        
        Field[] camps = classe.getDeclaredFields();
        
        
        Arrays.sort(camps, new OrdenarCampClasseAlfabeticament());
        int ncamps = camps.length;
        
        
        for (Field f : camps) {
            columnNames.addElement(f.getName().substring(3));
        }
        //columnNames.addElement("objecte");
        if (resultSet.size() != 0) {
            
            Vector<Method> methods = new Vector(ncamps +1);
            try {

                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(classe).getPropertyDescriptors();
                Arrays.sort(descriptors, new OrdenarMetodeClasseAlfabeticament());
                for (PropertyDescriptor pD : descriptors) {
                    Method m = pD.getReadMethod();
                    if (m != null & !m.getName().equals("getClass")) {
                        methods.addElement(m);
                    }
                }

            } catch (IntrospectionException ex) {
             Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);

            }
            for (Object m : resultSet) {
                Vector row = new Vector(ncamps + 1);

                for (Method mD : methods) {
                    try {
                        row.addElement(mD.invoke(m));
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                data.addElement(row);
            }
        }

        model=new DefaultTableModel(data, columnNames);
        taula.setModel(model);

        TableColumnModel tcm = taula.getColumnModel();
        TableColumn columna=tcm.getColumn(tcm.getColumnCount() - 1);
        
        TableColumn column;
        for (int i = 0; i < taula.getColumnCount(); i++) {
            column = taula.getColumnModel().getColumn(i);
            column.setMaxWidth(250);
        }
        return columna;
    }
    
    public void control() {

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
    
    private void ompliComboBox(ArrayList resultSet, JComboBox<Seu> ComboBox) {
        ComboBox.removeAllItems();
        ComboBox.addItem(null);
        for (Object m : resultSet) {
            ComboBox.addItem((Seu) m);
        }
    }

    private void clearEmpresa() {
        v.getNomjtxf().setText("");
        v.getCifjtxf().setText("");
        v.getSeusCB().setSelectedItem(null);
    }

    private void clearSeu() {
        v.getNomseujtx().setText("");
        v.getCiutatjtx().setText("");
        v.getTelefonjtx().setText(null);

    }

    
    public static class OrdenarMetodeClasseAlfabeticament implements Comparator {

        public int compare(Object o1, Object o2) {

            Method mo1 = ((PropertyDescriptor) o1).getReadMethod();
            Method mo2 = ((PropertyDescriptor) o2).getReadMethod();

            if (mo1 != null && mo2 != null) {
                return (int) mo1.getName().compareToIgnoreCase(mo2.getName());
            }

            if (mo1 == null) {
                return -1;

            } else {
                return 1;
            }
        }
    }

    public static class OrdenarCampClasseAlfabeticament implements Comparator {

        public int compare(Object o1, Object o2) {
            return (int) (((Field) o1).getName().compareToIgnoreCase(((Field) o2).getName()));
        }
    }

    
}
