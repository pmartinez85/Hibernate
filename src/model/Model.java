/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entitats.Empresa;
import entitats.Seu;
import excepcions.CentralException;
import excepcions.EmpresaRelExeption;
import excepcions.SeuRelException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import utils.HibernateUtil;

/**
 *
 * @author pedro
 */
public class Model {
    

    
    private static final Session sesion = HibernateUtil.getSessionFactory().openSession();
    
    
    
    private ArrayList<Empresa> empreses = new ArrayList();
    private EmpresaDAO<Empresa> empresa = new EmpresaDAO<>(Empresa.class, sesion);
    private ArrayList<Seu> seus = new ArrayList();
    private EmpresaDAO<Seu> seu = new EmpresaDAO<>(Seu.class, sesion);

    
    
    public Model(){
        actualitzaLlistes();
    }
    
    public ArrayList<Empresa> getEmpreses(){
        return empreses;
    }
    
    public ArrayList<Seu> getSeus(){
        return seus;
    }
    
     public void creaEmpresa(String nom, int cif, Seu central){
        Empresa emp = new Empresa(nom,cif);
        emp.set5_seus((List<Seu>) central);
        try{
            empresa.guarda(emp);
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
     
     public void modificaEmpresa(int id, String nom, int cif, Seu central){
        Empresa modificat = null;
        try{
            modificat = (Empresa) empresa.obte(id);
            modificat.set2_nomempresa(nom);
            modificat.set3_cif(cif);
            modificat.set4_central(central);
            empresa.actualitza(modificat);    
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
     
     public void eliminaEmpresa(int id) throws EmpresaRelExeption{
        Empresa elimina = null;
        try{
            elimina = (Empresa) empresa.obte(id);
            System.out.println(elimina);
            if(elimina.get4_central() == null){
                empresa.elimina(elimina);
            }else{
                throw new EmpresaRelExeption();
            }
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
     
     public void assignaCentral (int id, Seu central) throws CentralException{
        Empresa canvi = null;
        try{
            canvi = (Empresa) empresa.obte(id);
            if((central != null && central.get_5_empresa() != null) && (central.get_5_empresa() != canvi)){
                throw new CentralException();
            }
            canvi.set4_central(central);
            if(central != null){
                canvi.add5_novaseu(central);
                central.set_5_empresa(canvi);
                seu.actualitza(central);
            }  
            empresa.actualitza(canvi);
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
     
     public void creaSeu(String _2_nomseu, String _3_ciutat, int _4_telefon){
        Seu s = new Seu(_2_nomseu,_3_ciutat, _4_telefon);
        try{
            seu.guarda(s);
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
     
     
     public void eliminaSeu(int id) throws SeuRelException{
        Seu elimina = null;
        try{
            elimina = (Seu) seu.obte(id);
            if(elimina.get_5_empresa() == null){
                seu.elimina(elimina);
            }else{
                throw new SeuRelException();
            }
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
     
     public void canviSeu(int id, String nomseu, String ciutat, int telefon){
        Seu canvi = null;
        try{
            canvi = (Seu) seu.obte(id);
            canvi.set2_nomseu(nomseu);
            canvi.set3_ciutat(ciutat);
            canvi.set4_telefon(telefon);
            seu.actualitza(canvi);    
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();
    }
    
    public void deassignaSeu(Seu s, int id_empresa){
        Empresa canviat = null;
        try{
           if(s != null){
              s.set_5_empresa(null);
              seu.actualitza(s);  
           } 
           canviat = (Empresa) empresa.obte(id_empresa);
           canviat.del5_novaseu(s);
           empresa.actualitza(canviat);
        }catch(HibernateException e){
            tractaExcepcio(e);
        }
        actualitzaLlistes();        
    }    
     
     
    
    private void actualitzaLlistes(){
        seus = (ArrayList) seu.obtenLlista();
        empreses = (ArrayList) empresa.obtenLlista();
 
    }
    
    private void tractaExcepcio(HibernateException e) {
        System.out.println(e.getMessage());
    }
    
    public void tancaSessio() {
        try {
            sesion.close();
        }catch(HibernateException e){
            tractaExcepcio(e);
        } 
    }
}
