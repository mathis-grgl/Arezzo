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
    private List<String> listNotesABC, listNotes, listNotesWOctaves;

    public Arezzo(){
        //Déclaration synthesizer
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        //Déclaration partition
        partition = new Partition(synthesizer);
        partition.setTitre("");
        partition.setMelodie("");

        //Déclaration variable Arezzo
        nouveauProjet = false;
        hauteur = "medium";
        duree = "croche";
        melodie = "";
        titre = "Nouveau projet";
        temps = 0;
        tempo = 180;

        //Initialisation de la liste des notes en version ABC
        listNotesABC = List.of("C","^C","D","^D","E","F","^F","G","^G","A","^A","B");

        //Initialisation de la liste des notes en version classique
        listNotes = List.of("Do","DoDiese","Re","ReDiese","Mi","Fa","FaDiese","Sol","SolDiese","La","LaDiese","Si");

        //Initialisation de la liste des notes en version octaves
        listNotesWOctaves = List.of("C,","^C,","D,","^D,","E,","F,","^F,","G,","^G,","A,","^A,","B,","C","^C","D","^D","E","F","^F","G","^G","A","^A","B","c","^c","d","^d","e","f","^f","g","^g","a","^a","b");

        listMelodie = new ArrayList<>();

        //Bug sur certains pc de la faculté qui empêche le lancement (dû à des problèmes de son)
        //partition.setVolume(80);
    }

    public void resetAll(){
        partition.setInstrument("Piano");
        partition.setMelodie("");
        listMelodie = new ArrayList<>();
        melodie = "";
        titre = "Nouveau projet";
        tempo = 180;
        partition.setTempo((int) tempo);

        //Bug sur certains pc de la faculté qui empêche le lancement (dû à des problèmes de son)
        //partition.setVolume(80);

        temps = 1;
        nouveauProjet = true;
    }

    public void deleteMelodie(){
        partition.play("");
        partition.setMelodie("");
        listMelodie.clear();
        melodie = "";
        temps = 1;
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
                    concatenation.append("1");
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

    public void transposerNotesArezzo(int entier){
        ArrayList<String> copieTemporaireMelodie = new ArrayList<>(listMelodie);
        for (int i = 0; i < copieTemporaireMelodie.size(); i++) {
            //Evite de modifier les barre de séparation
            if(!listMelodie.get(i).equals("|")) {
                //Permet d'extraire les particularités des notes et de faire une liste sans ces particularités
                String test = String.valueOf(listMelodie.get(i).charAt(listMelodie.get(i).length() - 1));
                copieTemporaireMelodie.set(i, copieTemporaireMelodie.get(i).substring(0, copieTemporaireMelodie.get(i).length() - 1));

                //Fait les modifications de tons sur la copie puis sur la liste en elle-même
                int indexMelodieListe = listNotesWOctaves.indexOf(copieTemporaireMelodie.get(i));
                int indexTransposition = indexMelodieListe + entier;
                if (indexTransposition > 35) indexTransposition = 35;

                copieTemporaireMelodie.set(i, listNotesWOctaves.get(indexTransposition));
                copieTemporaireMelodie.set(i, copieTemporaireMelodie.get(i) + test);
                listMelodie.set(i, copieTemporaireMelodie.get(i));
            }
        }
        convertirListEnMelodie();
        setMelodie(melodie);
        notifierObservateur();
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

    public void setTempo(double tempo) {
        this.tempo = tempo;
        partition.setTempo((int) tempo);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Partition getPartition() {
        return partition;
    }

    public void fermerPartition(){
        partition.close();
    }

    public Image getImage(){
        return partition.getImage();
    }

    public int getTempo() {
        return (int) tempo;
    }
}
