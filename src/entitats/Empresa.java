/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */
@Entity
@Table(name = "empreses")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int _1_idempresa;
    
    @Column(name = "nom")
    public String _2_nomempresa;
    
    @Column(name = "cif")
    public int _3_cif;
    
    @OneToOne(optional=true)
    @JoinColumn(name = "central")
    private Seu _4_central;
   
    @OneToMany(mappedBy="_5_empresa")
    private List<Seu> _5_seus = new ArrayList<>(); 
    

    public int get1_idempresa() {
        return _1_idempresa;
    }

    public void set1_idempresa(int _1_idempresa) {
        this._1_idempresa = _1_idempresa;
    }

    public String get2_nomempresa() {
        return _2_nomempresa;
    }

    public void set2_nomempresa(String _2_nomempresa) {
        this._2_nomempresa = _2_nomempresa;
    }

    public long get3_cif() {
        return _3_cif;
    }

    public void set3_cif(int _3_cif) {
        this._3_cif = _3_cif;
    }
    
       public Seu get4_central() {
        return _4_central;
    }

    public void set4_central(Seu _4_central) {
        this._4_central = _4_central;
    }

    public List<Seu> get5_seus() {
        return _5_seus;
    }

    public void set5_seus(List<Seu> _5_seus) {
        this._5_seus = _5_seus;
    }
    
    public void add5_novaseu(Seu seu) {
        if(!this._5_seus.contains(seu)) this._5_seus.add(seu);
    }
    
    public void del5_novaseu(Seu seu) {
        this._5_seus.remove(seu);
    }

    public Empresa(String _2_nomempresa, int _3_cif) {
        this._2_nomempresa = _2_nomempresa;
        this._3_cif = _3_cif;
    }

    public Empresa() {
    }
    
    

    
}