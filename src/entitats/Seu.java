/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitats;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */
@Entity
@Table(name = "seus")
public class Seu{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id")
        private int _1_idseu;
        
        @Column(name = "nom")
        private String _2_nomseu;
        
        @Column(name = "ciutat")
	private String _3_ciutat;
        
        @Column(name = "telefon")
	private int _4_telefon;
        
       // @OneToOne(mappedBy="seus",optional=true) 
        @ManyToOne()
        @JoinColumn(name="empresa_id", nullable=true)
        private Empresa _5_empresa;
        
      
	// ==================================================
	// Creem Getters i Setters per totes les variables
	// ==================================================

    public int get1_idseu() {
        return _1_idseu;
    }

    public void set1_idseu(int id) {
        this._1_idseu = id;
    }

    public String get2_nomseu() {
        return _2_nomseu;
    }

    public void set2_nomseu(String nom) {
        this._2_nomseu = _2_nomseu;
    }

    public String get3_ciutat() {
        return _3_ciutat;
    }

    public void set3_ciutat(String _3_ciutat) {
        this._3_ciutat = _3_ciutat;
    }

    public int get4_telefon() {
        return _4_telefon;
    }

    public void set4_telefon(int _4_telefon) {
        this._4_telefon = _4_telefon;
    }

    public Empresa get_5_empresa() {
        return _5_empresa;
    }

    public void set_5_empresa(Empresa _5_empresa) {
        this._5_empresa = _5_empresa;
    }

    public Seu(int _1_idseu, String _2_nomseu, String _3_ciutat, int _4_telefon) {
        this._1_idseu = _1_idseu;
        this._2_nomseu = _2_nomseu;
        this._3_ciutat = _3_ciutat;
        this._4_telefon = _4_telefon;
    }

    public Seu(String _2_nomseu, String _3_ciutat, int _4_telefon) {
        this._2_nomseu = _2_nomseu;
        this._3_ciutat = _3_ciutat;
        this._4_telefon = _4_telefon;
    }

   

    public Seu() {
    }

    @Override
    public String toString() {
        return _2_nomseu;
    }

    
 
	
        
      
 

    
}
 
