/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.dretve;

import controler.misc.MetodeMisc;
import view.njuScrapalo.Pocetna;
import static view.njuScrapalo.Pocetna.getPostavke;

/**
 *
 * @author iduras
 */
public class DretvaAzuriranje extends Thread{
    
    private static DretvaAzuriranje dretva = new DretvaAzuriranje();
    
    private DretvaAzuriranje() {
    }
    
    @Override
    public void interrupt() {
        System.out.println("Dretva je završila s radom.");
        Pocetna.prikaziPoruku("Greška u dretvi za ažuriranje.");
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while (true) {  
            try {
                MetodeMisc.azurirajOglase(Pocetna.getPocetna());
                Thread.sleep(Integer.parseInt(getPostavke().getProperty("intervalUMinutama")) * 1000 * 60);
            } catch (InterruptedException ex) {
                Pocetna.prikaziPoruku("greška kod ažuriranja");
//                Logger.getLogger(DretvaSat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public static DretvaAzuriranje getDretva() {
        return dretva;
    }
}
