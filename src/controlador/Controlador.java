/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entitats.Empresa;
import entitats.Seu;
import excepcions.CentralException;
import excepcions.EmpresaRelExeption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
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
    private int filasel = -1;
    private int filaselSeu = -1;
    
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

    private void carregaTaula(ArrayList resultSet, JTable taula, Class<?> classe) {
        if (classe == Empresa.class) {
            filasel = -1;
        }
        if (classe == Seu.class) {
            filaselSeu = -1;
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

        model=new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        
        taula.setModel(model);

        TableColumn column;
        for (int i = 0; i < taula.getColumnCount(); i++) {
            column = taula.getColumnModel().getColumn(i);
            column.setMaxWidth(250);
        }
        if (classe == Seu.class) {
            ompliComboBox(resultSet, v.getSeusCB());
        }
       
    }
    
    private void control() {
        
        v.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                m.tancaSessio();
                System.exit(0);
            }

        });

        v.getInsertarbtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!"".equals(v.getNomjtxf().getText().trim())) {
                    try {
                        m.creaEmpresa(
                                v.getNomjtxf().getText().trim(),
                                Integer.valueOf(v.getCifjtxf().getText().trim()),
                                (Seu) v.getSeusCB().getSelectedItem()
                        );
                        carregaTaula(m.getEmpreses(), v.getEmpresataula(), Empresa.class);
                        clearEmpresa();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(v, "El cif ha de ser un enter", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(v, "El nom no pot estar buit", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
        
        
        v.getEmpresataula().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                filasel = v.getEmpresataula().getSelectedRow();
                if (filasel == -1) {
                    clearEmpresa();
                } else {
                    v.getNomjtxf().setText((String) v.getEmpresataula().getValueAt(filasel, 1));
                    v.getCifjtxf().setText(v.getEmpresataula().getValueAt(filasel, 2).toString());
                    v.getSeusCB().setSelectedItem(
                            v.getEmpresataula().getValueAt(filasel, 3) == null
                            ? null : (Seu) v.getEmpresataula().getValueAt(filasel, 3));
                }
            }

        });
        
        v.getModificarbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                try {
                    if (filasel != -1) {
                        
                        if (!"".equals(v.getNomjtxf().getText().trim())) {
                            if(v.getSeusCB().getSelectedItem() == null){
                                m.treuRelEmpresa((Seu) v.getEmpresataula().getValueAt(filasel, 3), Integer.valueOf(v.getEmpresataula().getValueAt(filasel, 0).toString()));
                            }
                            m.modificaEmpresa(
                                    Integer.valueOf(v.getEmpresataula().getValueAt(filasel, 0).toString()),
                                    v.getNomjtxf().getText().trim(),
                                    Integer.valueOf(v.getCifjtxf().getText().trim()),
                                    (Seu) v.getSeusCB().getSelectedItem()
                            );
                            carregaTaula(m.getEmpreses(), v.getEmpresataula(), Empresa.class);
                            carregaTaula(m.getSeus(), v.getSeutaula(), Seu.class);
                            clearEmpresa();
                        } else {
                            JOptionPane.showMessageDialog(v, "El valor del nom no pot ser buit", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(v, "S'ha de seleccionar un registre per poder modificar-lo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(v, "El valor d'anys ha de ser un enter", "Error", JOptionPane.ERROR_MESSAGE);
                } 
//                catch (CentralException ex){
//                    JOptionPane.showMessageDialog(v, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
                

            }
        });
        
        v.getEsborrarbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (filasel != -1) {
                        m.eliminaEmpresa(Integer.valueOf(v.getEmpresataula().getValueAt(filasel, 0).toString()));
                        carregaTaula(m.getEmpreses(), v.getEmpresataula(), Empresa.class);
                        clearEmpresa();
                    } else {
                        JOptionPane.showMessageDialog(v, "Si no tries res no ho puc borrar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (EmpresaRelExeption ex) {
                    JOptionPane.showMessageDialog(v, ex.getMessage(), "Error en eliminar degut a una relació", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        v.getModificarbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                v.getjPanel1().removeAll();
                v.getjPanel1().repaint();
                v.getjPanel1().revalidate();

                v.getjPanel1().add(v.getjPanel2());
                v.getjPanel1().repaint();
                v.getjPanel1().revalidate();
            }

        });
        
        
        
        
        
        
    }
    
    
    
    
    
    private void ompliComboBox(List result, JComboBox ComboBox) {
        ArrayList resultSet = new ArrayList(result);
        ComboBox.removeAllItems();
        if(result.isEmpty()){
            
        }else{
            ComboBox.addItem(null);
            for (Object m : resultSet) {
                ComboBox.addItem(m);
            }  
        }
        
    }
    
    private void emplenaJList(JList jlist, List list) {
        ArrayList arrayList = new ArrayList(list);
        DefaultListModel lm = new DefaultListModel();
        for (Object o : arrayList){
            lm.addElement(o);
        }
        jlist.setModel(lm);
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
