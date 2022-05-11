/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.misc;

import controler.oglas.MetodeOglasa;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import view.njuScrapalo.Pocetna;
import static view.njuScrapalo.Pocetna.postavke;
import view.obrasci.ObrazacPostavki;

/**
 *
 * @author ahilis001
 */
public class MetodeMisc {
    
    static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    
    /**
     * Čita sadržaj datoteke s putanje.
     * @param putanja
     * @return 
     */
    static public StringBuilder citanjeIzDatoteke(String putanja){
        
        try {
            //čitanje iz datoteke
            BufferedReader br = new BufferedReader(new FileReader(putanja));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ( (line = br.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
            }
            
            return builder;
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metoda za otvaranje datoteke konfiguracije.
     */
    public static void ucitajKonfiguraciju(){
        
        File configFile = new File("konfiguracija.xml");
        
        try {
            FileReader reader = new FileReader(configFile);
            InputStream inputStream = new FileInputStream(configFile);
            //za datoteku .properties
//            postavke.load(inputStream);

            //za datoteku .xml
            postavke.loadFromXML(inputStream);
            reader.close();
        } catch (Exception ex) {
//            MetodeTajmera.dodajULog("Pocetna - ucitajKonfiguraciju - Greška kod učitavanja konfiguracijse datoteke.");
        } 
    }
    
    /**
     * Ažurira jFrame nakon dogvaćanja oglasa
     * @param jpPocetna
     */
    public static void azurirajOglase(JFrame jpPocetna){
        try {
            //ažuriranje poruke
            Pocetna.getJtPoruke().setText(formatter.format(new Date(System.currentTimeMillis())) + " - ažurirano");
            
            MetodeOglasa.izradiListuOglasa(MetodeOglasa.izradiListuURLa());
        } catch (IOException ex) {
            Pocetna.getJtPoruke().setText(formatter.format(new Date(System.currentTimeMillis())) + " - greška kod ažuriranja");
        }
    }
    
    /**
     * Ažurira konfiguracijsku datoteku.
     * @param properties
     * @param op
     */
    public static void azurirajKonfiguraciju(Properties properties, ObrazacPostavki op){
        try {
            //postavljanje postavki iz prosljeđenog ObrascaPostavki i otvaranje streama za upis u .xml datoteku
            try (FileOutputStream fos = new FileOutputStream("konfiguracija.xml")) {
                properties.setProperty("kljucneRijeci", op.getTfUpit().getText());
                properties.setProperty("cijenaMin", op.getTfCijenaMin().getText());
                properties.setProperty("cijenaMax", op.getTfCijenaMax().getText());
                properties.setProperty("lokacijaX", op.getTfCoX().getText());
                properties.setProperty("lokacijaY", op.getTfCoY().getText());
                properties.setProperty("maxOglasaPoUpitu", op.getTfBrojOglasa().getText());
                properties.setProperty("intervalUMinutama", op.getTfInterval().getText());
                properties.setProperty("sortiranje", op.getListaSortiranja().get(op.getJcSortiranje().getSelectedIndex()).getKey());
                
                //spremanje u .xml datoteku s komentarom
                properties.storeToXML(fos, "ključne riječi se razdvajaju zarezom - ','");
            }
        } catch (Exception ex) {
            Pocetna.prikaziPoruku("greška kod ažuriranja postavki");
        }
    }
    
    /**
     * Daje ikonu za prikaz slike oglasa.
     * @return 
     */
    public static Image dajSliku(String urlSlike){
        Image slika = null;
        
        try {
            //za prikaz slike iz URL-a
            URL url = new URL(urlSlike);
            slika = ImageIO.read(url);

            //za prikaz slike iz mape
//            File file = new File(Pocetna.getPostavke().getProperty("putanjaMapeIkona") + "/" + ikonaID + ".png");
//            ikona = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return slika;
    }
    
    /**
     * Čita sadržaj datoteke s putanje.
     * @param url
     * @return 
     */
    static public StringBuilder citanjeIzURLa(URL url){
        
        try {
            
//            //za povlačenje preko curl-a, ne radi na MacOS-u
//            String[] command = {"curl ", "-H " ,"POST ", "http://api.openweathermap.org/data/2.5/forecast/daily?q="+gradDrzava+"&units=metric&appid=" + Pocetna.getPostavke().getProperty("meteoOMWKey")};
//            ProcessBuilder process = new ProcessBuilder(command); 
//            Process p;
//            
//            //za povlačenje preko curl-a, ne radi na MacOS-u
//            p = process.start();
//            BufferedReader br =  new BufferedReader(new InputStreamReader(p.getInputStream()));
//            StringBuilder builder = new StringBuilder();
//            String line = null;
//            
//            while ( (line = br.readLine()) != null) {
//                    builder.append(line);
//                    builder.append(System.getProperty("line.separator"));
//            }
            
            //čitanje iz URL
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            
            while ( (line = br.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
            }
            
            return builder;
        } 
        
        catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Upisuje proslijedeni sadržaj u datoteku s putanje.
     * @param nazivDatoteke
     * @param sadrzaj
     */
    public static void upisUDatoteku(String nazivDatoteke, String sadrzaj){
        
        try {

            FileOutputStream outputStream = new FileOutputStream(nazivDatoteke);
            byte[] strToBytes = sadrzaj.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
    
    /**
     * Ako nije povučen niti jedan dogadjaj u datoteku dogadjaja, 
     * postavlja da je nova datoteka dogadjaja kopija stare.
     * @param ulaznaDatoteka
     */
    public static void brisiKopijuDatoteke(String ulaznaDatoteka){
        
        File source = new File(ulaznaDatoteka);
        File destination = new File("kopija_" + ulaznaDatoteka);
        
        //ako ne postoji original ili je prazan
        //briše se stari original, kreira novi i u njega upisuje sadrzaj kopije
        //na kraju se briše kopija
        if (!source.exists() || source.length() == 0) {
            source.delete();
            kopirajDatoteku("kopija_" + ulaznaDatoteka, ulaznaDatoteka);
            destination.delete();
        } 
        
        //ako je povućen novi original
        else {
            destination.delete();
        }
    }
    
    /**
     * Kopira datoteku.
     * @param ulaznaDatoteka
     */
    public static void kopirajDatoteku(String ulaznaDatoteka){
        File source = new File(ulaznaDatoteka);
            File destination = new File("kopija_" + ulaznaDatoteka);
        try {
            Files.copy(source.toPath(), destination.toPath());
        } catch (IOException ex) {
//            ex.printStackTrace();
//            MetodeTajmera.dodajULog("MetodeMisc - kopirajDatoteku - Greška kod kopiranja datoteke.");
        }
    }
    
    /**
     * Kopira datoteku.
     * @param ulaznaDatoteka
     * @param izlaznaDatoteka
     */
    public static void kopirajDatoteku(String ulaznaDatoteka, String izlaznaDatoteka){
        try {
            Files.copy(new File(ulaznaDatoteka).toPath(), new File(izlaznaDatoteka).toPath());
        } catch (IOException ex) {
//            ex.printStackTrace();
//            MetodeTajmera.dodajULog("MetodeMisc - kopirajDatoteku - greška kod kopiranja datoteke.");
        }
    } 
    
}
