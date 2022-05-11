/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.obrasci;

import controler.misc.MetodeMisc;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.ComboItem;
import view.njuScrapalo.Pocetna;
import static view.njuScrapalo.Pocetna.getPostavke;

/**
 *
 * @author Ahilis
 */
public class ObrazacPostavki extends JFrame{
    
    TextField tfUpit, tfCijenaMax, tfCijenaMin, tfBrojOglasa, tfInterval, tfCoX, tfCoY;
    JComboBox<String> jcSortiranje = new JComboBox<>();
    ArrayList<ComboItem> listaSortiranja = new ArrayList<>();
    
    public ObrazacPostavki(){
    }
    
    /**
     * Generira obrazac postavki.
     */
    public void generirajObrazac(){
        
        //inicijalizacija jFramea postavki
        JFrame jfObrazacPostavki = new JFrame();
        
        //postavljanje poravnanja i layouta jFramea
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.BOTH;

        jfObrazacPostavki.setLayout(new GridBagLayout());
        
        //jPanel za ključne riječi
        JPanel jPUpit = new JPanel(new GridBagLayout());
        jPUpit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ključne riječi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        
        //tf za upit
        tfUpit = new TextField(getPostavke().getProperty("kljucneRijeci"));
        c.gridx = 0;
        c.gridy = 0;
        jPUpit.add(tfUpit, c);
        
        //jPanel za cijenu
        JPanel jPCijena = new JPanel(new GridBagLayout());
        jPCijena.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cijena", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        
        c.gridx = 0;
        c.gridy = 0;
        jPCijena.add(new JLabel("Max cijena:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        tfCijenaMax = new TextField(getPostavke().getProperty("cijenaMax"));
        jPCijena.add(tfCijenaMax, c);
        
        c.gridx = 0;
        c.gridy = 1;
        jPCijena.add(new JLabel("Min cijena:"), c);
        
        c.gridx = 1;
        c.gridy = 1;
        tfCijenaMin = new TextField(getPostavke().getProperty("cijenaMin"));
        jPCijena.add(tfCijenaMin, c);
        
        c.gridx = 0;
        c.gridy = 2;
        jPCijena.add(new JLabel("Sortiranje:"), c);
        
        //jCombobox
        c.gridx = 1;
        c.gridy = 2;
        
        listaSortiranja.add(new ComboItem("new","Najnovije"));
        listaSortiranja.add(new ComboItem("old","Najstarije"));
        listaSortiranja.add(new ComboItem("expensive","Najskuplje"));
        listaSortiranja.add(new ComboItem("cheap","Najjeftinije"));

        for (ComboItem comboItem : listaSortiranja) {
            jcSortiranje.addItem(comboItem.getValue());
            if (comboItem.getKey().equals(Pocetna.getPostavke().getProperty("sortiranje"))) {
                jcSortiranje.setSelectedIndex(jcSortiranje.getItemCount() - 1);
            }
        }
        
        jPCijena.add(jcSortiranje, c);
        
        //jPanel za druge postavke
        JPanel jPDrugePostavke= new JPanel(new GridBagLayout());
        jPDrugePostavke.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Druge postavke", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        
        //tf za broj oglasa
        c.gridx = 0;
        c.gridy = 0;
        jPDrugePostavke.add(new JLabel("Broj oglasa po pojmu:"), c);
        
        tfBrojOglasa = new TextField(getPostavke().getProperty("maxOglasaPoUpitu"));
        c.gridx = 1;
        c.gridy = 0;
        jPDrugePostavke.add(tfBrojOglasa, c);
        
        //tf za interval
        c.gridx = 0;
        c.gridy = 1;
        jPDrugePostavke.add(new JLabel("Ažuriranje oglasa u min:"), c);
        
        tfInterval = new TextField(getPostavke().getProperty("intervalUMinutama"));
        c.gridx = 1;
        c.gridy = 1;
        jPDrugePostavke.add(tfInterval, c);
        
        //tf za x
        c.gridx = 0;
        c.gridy = 2;
        jPDrugePostavke.add(new JLabel("Koordinata X:"), c);
        
        tfCoX = new TextField(getPostavke().getProperty("lokacijaX"));
        c.gridx = 1;
        c.gridy = 2;
        jPDrugePostavke.add(tfCoX, c);
        
        //tf za interval
        c.gridx = 0;
        c.gridy = 3;
        jPDrugePostavke.add(new JLabel("Koordinata Y:"), c);
        
        tfCoY = new TextField(getPostavke().getProperty("lokacijaY"));
        c.gridx = 1;
        c.gridy = 3;
        jPDrugePostavke.add(tfCoY, c);
        
        //dodavanje jButtona za spremanje i zatvaranje
        JButton jbSpremiSveIZatvori = new JButton("Spremi i zatvori");
        jbSpremiSveIZatvori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSpremiSveIZatvorictionPerformed(jfObrazacPostavki, evt);
            }
        });
        
        c.gridx = 0;
        c.gridy = 4;
        jfObrazacPostavki.add(jbSpremiSveIZatvori, c);
        
        //dodavanje panela u jFrame
        c.gridx = 0;
        c.gridy = 0;
        jfObrazacPostavki.add(jPUpit, c);
        
        c.gridy = 1;
        jfObrazacPostavki.add(jPCijena, c);
  
        c.gridy = 2;
        jfObrazacPostavki.add(jPDrugePostavke, c);
        
        jfObrazacPostavki.setUndecorated(true);
        jfObrazacPostavki.revalidate();
        jfObrazacPostavki.pack();
        jfObrazacPostavki.setVisible(true);
    }    
    
    /**
     * metoda spremanje postavki i zatvaranje obrasca postavki
     * @param jfObrazac
     * @param evt 
     */
    private void jbSpremiSveIZatvorictionPerformed(JFrame jfObrazac, java.awt.event.ActionEvent evt) { 
        MetodeMisc.azurirajKonfiguraciju(Pocetna.getPostavke(), this);
        jfObrazac.dispose();
        MetodeMisc.azurirajOglase(Pocetna.getPocetna());
    } 

    public TextField getTfUpit() {
        return tfUpit;
    }

    public void setTfUpit(TextField tfUpit) {
        this.tfUpit = tfUpit;
    }

    public TextField getTfCijenaMax() {
        return tfCijenaMax;
    }

    public void setTfCijenaMax(TextField tfCijenaMax) {
        this.tfCijenaMax = tfCijenaMax;
    }

    public JComboBox<String> getJcSortiranje() {
        return jcSortiranje;
    }

    public void setJcSortiranje(JComboBox<String> jcSortiranje) {
        this.jcSortiranje = jcSortiranje;
    }

    public TextField getTfCijenaMin() {
        return tfCijenaMin;
    }

    public TextField getTfBrojOglasa() {
        return tfBrojOglasa;
    }

    public void setTfBrojOglasa(TextField tfBrojOglasa) {
        this.tfBrojOglasa = tfBrojOglasa;
    }

    public TextField getTfInterval() {
        return tfInterval;
    }

    public void setTfInterval(TextField tfInterval) {
        this.tfInterval = tfInterval;
    }

    public TextField getTfCoX() {
        return tfCoX;
    }

    public void setTfCoX(TextField tfCoX) {
        this.tfCoX = tfCoX;
    }

    public TextField getTfCoY() {
        return tfCoY;
    }

    public void setTfCoY(TextField tfCoY) {
        this.tfCoY = tfCoY;
    }

    public void setTfCijenaMin(TextField tfCijenaMin) {
        this.tfCijenaMin = tfCijenaMin;
    }

    public ArrayList<ComboItem> getListaSortiranja() {
        return listaSortiranja;
    }

    public void setListaSortiranja(ArrayList<ComboItem> listaSortiranja) {
        this.listaSortiranja = listaSortiranja;
    }
}
