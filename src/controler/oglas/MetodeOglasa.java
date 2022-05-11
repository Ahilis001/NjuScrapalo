/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.oglas;

import controler.misc.MetodeMisc;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Oglas;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import view.njuScrapalo.Pocetna;
import static view.njuScrapalo.Pocetna.getPostavke;

/**
 *
 * @author ahilis001
 */
public class MetodeOglasa {
    
    /**
     * izrađuje listu oglasa iz URL-a
     * @param listaURLaOglasa
     * @throws java.io.IOException
     */
    public static void izradiListuOglasa(ArrayList<String> listaURLaOglasa) throws IOException{
        
        //ciscenje liste panela s oglasima i postavljanje brojaca panela (služi za točan naziv panela - povlači ključne riječi)
        Oglas.getListaOglasa().clear();
        int intBrojacJPanela = 0;
       
        //za svaki URL iz liste URL-a
        for (String url : listaURLaOglasa) {
            
            //deklaracija liste koja sadrži objekte oglasa
            ArrayList<Oglas> listaObjektaOglasa = new ArrayList();
            
            //deklaracija jPanela u kojem će biti prikazani oglasi iz liste objekata oglasa
            JPanel jpOglasiUpita = new JPanel();
            
            //dohvaćanje stranice iz URL-a
            Document document = Jsoup.connect(url).get();
        
            try {
                //uzimanje odgovarajuće liste oglasa iz dokumenta
                Element listaOglasa = document.select("ul.EntityList-items").get(document.select("ul.EntityList-items").size() - 2);

                //inicijalizacija max broja oglasa po upitu
                int intBrojOglasaZaPrikazPoUpitu = Integer.parseInt((String) Pocetna.getPostavke().get("maxOglasaPoUpitu"));
                
                //selektiranje svakog oglasa iz dokumenta
                for (org.jsoup.nodes.Element element : listaOglasa.select("article.entity-body.cf")) {
                    
                    //ako nije dostignut max broj oglasa po upitu, dodaje se novi oglas, ako ga ima
                    if (listaObjektaOglasa.size() < intBrojOglasaZaPrikazPoUpitu) {
                        
                        //deklaracija Oglasa
                        Oglas noviOglas = new Oglas();

                        //popunjavanje
                        noviOglas.setNaziv(element.select("h3.entity-title").text());
                        noviOglas.setIznos(element.select("li.price-item").text());
                        noviOglas.setDatum(element.select("time.date.date--full").text());
                        noviOglas.setOpis(element.select("div.entity-description-main").text());
                        noviOglas.setSlika("https:" + element.select("img").attr("data-src"));
                        noviOglas.setPoveznica("https://www.njuskalo.hr" + element.select("a").attr("href"));
                        
                        //dodavanje oglasa u listu objekata oglasa
                        listaObjektaOglasa.add(noviOglas);
                    }
                }  
                
                //generiranje izgleda jPanela
                generirajPrikazJPanelaZaUpit(jpOglasiUpita, listaObjektaOglasa);
                
                //postavljanje naziva jPanela
                jpOglasiUpita.setBorder(javax.swing.BorderFactory.createTitledBorder(null, listaKlucnihRijeci(getPostavke().getProperty("kljucneRijeci"))[intBrojacJPanela].replace("+", " "), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        
                //dodavanje u glavni jPanel za prikaz
                Oglas.getListaOglasa().add(jpOglasiUpita);
                intBrojacJPanela ++;
            } 
            catch (ArrayIndexOutOfBoundsException e) {
                jpOglasiUpita.setBorder(javax.swing.BorderFactory.createTitledBorder(null, listaKlucnihRijeci(getPostavke().getProperty("kljucneRijeci"))[intBrojacJPanela].replace("+", " "), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0)));
        
                jpOglasiUpita.add(new JLabel("Nema rezultata za ovaj upit."));
                Oglas.getListaOglasa().add(jpOglasiUpita);
                intBrojacJPanela ++;
            }
            
            catch(NumberFormatException e){
                Pocetna.prikaziPoruku("Upiši max broj oglasa brojkom.");
            }            
        }   
        
        //resetiranje brojaca panela
        intBrojacJPanela = 0;

        //brisanje eventualnih ranijih oglasa i postavljanje layouta
        Pocetna.getJpPrikazOglasa().removeAll();
        Pocetna.getJpPrikazOglasa().setLayout(new BoxLayout(Pocetna.getJpPrikazOglasa(), BoxLayout.Y_AXIS));
        
        //dodavanje jPanela upita iz liste
        for (JPanel jPanel : Oglas.getListaOglasa()) {
            Pocetna.getJpPrikazOglasa().add(jPanel);
        }
        
        //osvježavanje jPanela i jFramea
        Pocetna.getJpPrikazOglasa().revalidate();
        Pocetna.getPocetna().pack();
        Pocetna.getPocetna().revalidate();
    }
    
    /**
     * Sluzi za izradu URL-a 
     * @param kljucnaRijec
     * @return izradeniURL
     */
    static public String izradiURL(String kljucnaRijec){
        String izradeniURL = "https://www.njuskalo.hr/index.php?ctl=search_ads&keywords=";
        izradeniURL += kljucnaRijec + "&price[min]=" + getPostavke().getProperty("cijenaMin");
        izradeniURL += "&price[max]=" + getPostavke().getProperty("cijenaMax");
        izradeniURL += "&sort=" + getPostavke().getProperty("sortiranje");
        return izradeniURL;
    }
    
    /**
     * Vraća listu gotovih URL-a
     * @return lista gotovih URL-a
     */
    public static ArrayList<String> izradiListuURLa(){
        
        //inicijalizacija liste URL-a
        ArrayList<String> listaURLa = new ArrayList<>();
        
        //dohvaćanje polja ključnih rijeći
        String[] split = listaKlucnihRijeci(getPostavke().getProperty("kljucneRijeci"));
        
        //izrada djelomičnih URL-a iz polja ključnih rijeći
        for (String string : split) {
            listaURLa.add(izradiURL(string));
        }
        
        return listaURLa;
    }
    
    /**
     * Vraća polje ključnih riječi iz konfiguracijske datoteke.
     * @param kljucneRijeci
     * @return listaKljucnihRijeci 
     */
    public static String[] listaKlucnihRijeci(String kljucneRijeci){
        String[] lista;
    
        //dohvaćanje ključnih riječi i prepremanje za izradu url-a
        String strUpiti = getPostavke().getProperty("kljucneRijeci").replace(", ", ",");
        strUpiti = strUpiti.replace(" ", "+");
        
        //razdvajanje string ključnih riječi na polje događaja
        String patternString = ",";
        Pattern pattern = Pattern.compile(patternString);
        lista = pattern.split(strUpiti);
        
        return lista;
    }
    
    /**
     * Kreira jPanel s oglasima iz liste oglasa
     * @param jpOglasiUpita
     * @param listaOglasa
     * @return jpOglasiUpita
     */
    public static JPanel generirajPrikazJPanelaZaUpit(JPanel jpOglasiUpita, ArrayList<Oglas> listaOglasa){
        
        //postavljanje Layouta
        jpOglasiUpita.setLayout(new GridBagLayout()); 
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        
        //kreiranje izgleda oglasa iz liste oglasa
        for (Oglas oglas : listaOglasa) {
            
            //za sliku
            JLabel slika = new JLabel(new ImageIcon(MetodeMisc.dajSliku(oglas.getSlika()), oglas.getNaziv()));
            
            //metoda za otvaranje url-a nakon klika na sliku
            otvoriStranicu(slika, oglas.getPoveznica());
            jpOglasiUpita.add((slika), c);
            
            //za ostale podatke oglasa
            c.gridx ++;
            jpOglasiUpita.add(new JLabel(
                "<html>Naziv: "        + oglas.getNaziv() + "<br/>" +
                      "Datum objave: " + oglas.getDatum() + "<br/>" +
                      "Iznos: "        + oglas.getIznos() + "<br/>" +
                      "Opis: "         + oglas.getOpis()  + "<br/>"          
            ), c);
            
            //postavljanje na početni stupac i sljedeći red
            c.gridx = 0;
            c.gridy ++; 
        }
        
        jpOglasiUpita.revalidate();  
        return jpOglasiUpita;
    }
    
    /**
     * Za otvaranje url-a stranice u pregledniku nakon klika na sliku.
     * @param website
     * @param url 
     */
    static void otvoriStranicu(JLabel website, final String url) {
        website.setCursor(new Cursor(Cursor.HAND_CURSOR));
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                        Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException | IOException ex) {
                }
            }
        });
    }
    
}
