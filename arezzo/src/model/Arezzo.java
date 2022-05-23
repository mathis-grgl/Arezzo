package model;

import javafx.scene.image.Image;
import partition.Partition;
import ecouteur.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arezzo extends SujetObserve {
    private Boolean nouveauProjet;
    private Synthesizer synthesizer;
    private Partition partition;
    private String melodie, hauteur,duree,titre;
    private ArrayList<String> listMelodie;
    private double temps,tempo;
    private List<String> listNotesABC, listNotes;

    public Arezzo(){
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        partition = new Partition(synthesizer);
        partition.setTitre("");

        nouveauProjet = false;

        hauteur = "medium";
        duree = "croche";
        melodie = "";
        titre = "Nouveau projet";
        temps = 0;
        tempo = 180;
        partition.setMelodie(melodie);

        //Initialisation de la liste des notes en version ABC
        listNotesABC = new ArrayList<>();
        listNotesABC = List.of("C","^C","D","^D","E","F","^F","G","^G","A","^A","B");

        //Initialisation de la liste des notes en version classique
        listNotes = new ArrayList<>();
        listNotes = List.of("Do","DoDiese","Re","ReDiese","Mi","Fa","FaDiese","Sol","SolDiese","La","LaDiese","Si");

        listMelodie = new ArrayList<>();
        //partition.setVolume(80);
    }

    public Partition getPartition() {
        return partition;
    }

    public void resetAll(){
        partition.setInstrument("Piano");
        partition.setMelodie("");
        listMelodie = new ArrayList<>();
        melodie = "";
        titre = "Nouveau projet";
        tempo = 180;
        partition.setTempo((int) tempo);
        //partition.setVolume(80);
        temps = 1;
        nouveauProjet = true;
    }

    public Boolean getNouveauProjet() {
        return nouveauProjet;
    }

    public void setNouveauProjet(Boolean nouveauProjet) {
        this.nouveauProjet = nouveauProjet;
    }

    public void setHauteur(String hauteur){
        this.hauteur = hauteur;
    }

    public void setDuree(String duree){
        this.duree = duree;
    }

    public void addMelodie(String lettre){
        listMelodie.add(lettre);
        if(temps >= 4) {
            temps = 0;
            listMelodie.add("|");
        }
        convertirListEnMelodie();
    }

    public void convertirListEnMelodie(){
        StringBuilder concat = new StringBuilder();
        for (String lettre : listMelodie) {
            concat.append(lettre);
            concat.append(" ");
        }
        melodie = concat.toString();
    }

    public void convertirMelodieEnList(){
        String[] separation = melodie.split("\\s");
        List<String> listCopie = Arrays.asList(separation);
        listMelodie = new ArrayList<>();
        listMelodie.addAll(listCopie);
    }

    public void jouerMelodie() {
        convertirListEnMelodie();
        partition.play(melodie);
        partition.setMelodie(melodie);
    }

    public Image getImage(){
        return partition.getImage();
    }

    public String getNotationHauteurDuree(String lettre) {
        StringBuilder concatenation = new StringBuilder();
        String lettreABC = lettre;
        if (!lettre.equals("z") && !lettre.equals("|")) {
            switch (hauteur) {
                case "aigu":
                    lettreABC = lettreABC.toLowerCase();
                    concatenation.append(lettreABC);
                    break;
                case "grave":
                    concatenation.append(lettreABC);
                    concatenation.append(",");
                    break;
                case "medium":
                    concatenation.append(lettreABC);
            }
        } else {
            concatenation.append("z");
        }
            switch (duree) {
                case "croche":
                    concatenation.append("/");
                    temps+=0.5;
                    break;
                case "ronde":
                    concatenation.append("4");
                    temps+=4;
                    break;
                case "blanche":
                    concatenation.append("2");
                    temps+=2;
                    break;
                case "noire":
                    temps+=1;
                    break;
            }
        addMelodie(concatenation.toString());
        partition.setMelodie(melodie);
        return concatenation.toString();
    }

    public String conversionNotes(String note) {
        for (int i = 0; i < listNotes.size(); i++) {
            if(listNotes.get(i).equals(note)){
                return listNotesABC.get(i);
            }
        }
        return null;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMelodie() {
        return melodie;
    }

    public void setMelodie(String melodie) {
        this.melodie = melodie;
        convertirMelodieEnList();
        partition.setMelodie(melodie);
    }

    public void playMelodieVide(){
        partition.play("");
    }

    public void transposerNotesArezzo(int entier){
        System.out.println(melodie);
        String test = "";
        for (int j = 0; j < listNotesABC.size(); j++) {
            for (int i = 0; i < listMelodie.size(); i++) {
                String testPremiereLettre = String.valueOf(listMelodie.get(i).charAt(0));
                if(testPremiereLettre.equals("^")){
                    test = listMelodie.get(i).substring(2);
                } else {
                    test = listMelodie.get(i).substring(1);
                }
                System.out.println(test);
                System.out.println("test : "+listNotesABC.get(i)+test+" melodie : "+listMelodie.get(i));

            }
        }
        convertirListEnMelodie();
        System.out.println(melodie);
    }

    public void fermerPartition(){
        partition.close();
    }

    public int getTempo() {
        return (int) tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
        partition.setTempo((int) tempo);
    }
}
