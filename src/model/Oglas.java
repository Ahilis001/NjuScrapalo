/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Klasa Oglasa.
 * @author ahilis001
 */
public class Oglas {

    private static ArrayList<JPanel> listaOglasa = new ArrayList();
    
    String naziv = "";
    String iznos = "";
    String datum = "";
    String slika = "";
    String opis  = "";
    String poveznica = "";

    public Oglas() {
    }
    
    public Oglas(String naziv, String iznos, String datum, String slika, String opis, String poveznica) {
        this.naziv = naziv;
        this.iznos = iznos;
        this.datum = datum; 
        this.slika = slika; 
        this.opis  = opis; 
        this.poveznica = poveznica;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getIznos() {
        return iznos;
    }

    public void setIznos(String iznos) {
        this.iznos = iznos;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getPoveznica() {
        return poveznica;
    }

    public void setPoveznica(String poveznica) {
        this.poveznica = poveznica;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public static ArrayList<JPanel> getListaOglasa() {
        return listaOglasa;
    }

    public static void setListaOglasa(ArrayList<JPanel> listaOglasa) {
        Oglas.listaOglasa = listaOglasa;
    }
           
}
