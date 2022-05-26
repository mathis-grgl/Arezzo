package model;

import javafx.collections.ObservableList;
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
        partition.setPreferedMaxWidth(800);

        //Déclaration variable Arezzo
        nouveauProjet = false;
        hauteur = "medium";
        duree = "croche";
        melodie = "";
        titre = "Nouveau projet";
        temps = 0;
        tempo = 180;

        //Initialisation de la liste des notes en version ABC
        listNotesABC = List.of("C","^C","D","^D","E","F","^F","G","^G","A","^A","B","Z");

        //Initialisation de la liste des notes en version classique
        listNotes = List.of("Do","DoDiese","Re","ReDiese","Mi","Fa","FaDiese","Sol","SolDiese","La","LaDiese","Si","Silence");

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
        temps = 1;
        nouveauProjet = true;

        //Bug sur certains pc de la faculté qui empêche le lancement (dû à des problèmes de son)
        //partition.setVolume(80);
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
        setMelodie(melodie);
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

    public ArrayList<String> getListSansMesure(){
        ArrayList<String> returnList = new ArrayList<>(listMelodie);
        for (int i = 0; i <returnList.size(); i++) returnList.remove("|");
        return returnList;
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

    public void transposerNotesArezzo(int nbTransposition){
        for (int i = 0; i < listMelodie.size(); i++) {
            transposerNote(nbTransposition, i);
        }
        convertirListEnMelodie();
        notifierObservateur();
    }

    public String noteSansSurPlusMajuscule(String note){
        String noteSP = note;
        String charSP = String.valueOf(noteSP.charAt(noteSP.length()-1));
        if(charSP.equals("/") || charSP.equals("1") || charSP.equals("2") || charSP.equals("4"))
            noteSP = noteSP.replace(charSP,"");
        charSP = String.valueOf(noteSP.charAt(noteSP.length()-1));
        if(charSP.equals(","))
            noteSP = noteSP.replace(charSP,"");
        noteSP = noteSP.toUpperCase();
        return noteSP;
    }

    public String conversionNotesVersABC(String note) {
        for (int i = 0; i < listNotes.size(); i++) {
            if(listNotes.get(i).equals(note)){
                return listNotesABC.get(i);
            }
        }
        return null;
    }

    public String conversionNotesVersClassique(String note) {
        for (int i = 0; i < listNotesABC.size(); i++) {
            if(listNotesABC.get(i).equals(note)){
                return listNotes.get(i);
            }
        }
        return null;
    }

    public void supprimerNote(ObservableList<Integer> notes){
        for(int index : notes){
            listMelodie.remove(index);
        }
        convertirListEnMelodie();
        notifierObservateur();
    }

    public void transposerNoteComposition(int nbTransposition, ObservableList<Integer> notes){
        for (int index : notes) {
            transposerNote(nbTransposition, index);
        }
        convertirListEnMelodie();
        notifierObservateur();
    }

    private void transposerNote(int nbTransposition, int index) {
        //Evite de modifier les barre de séparation
        if(!listMelodie.get(index).equals("|")) {

            //Permet d'extraire les particularités des notes et de faire une liste sans ces particularités
            String symboleDuree = String.valueOf(listMelodie.get(index).charAt(listMelodie.get(index).length() - 1));
            listMelodie.set(index, listMelodie.get(index).replace(symboleDuree,""));

            //Permet de savoir l'index de la future note
            int indexMelodieListe = listNotesWOctaves.indexOf(listMelodie.get(index));
            int indexTransposition = indexMelodieListe + nbTransposition;
            if (indexTransposition > 35) indexTransposition = 35;
            if (indexTransposition < 0) indexTransposition = 0;

            //Fait les modifications de tons sur la note i
            listMelodie.set(index, listNotesWOctaves.get(indexTransposition) + symboleDuree);
        }
    }

    public boolean noteEstUnSilence(String note){
        return noteSansSurPlusMajuscule(note).equals("Z");
    }

    public String getOctaveNote(String note) {
        char checkLettre = note.charAt(note.length()-1);
        if(String.valueOf(checkLettre).equals(","))
            return "grave";
        if(Character.isLowerCase(checkLettre) && Character.isLetter(checkLettre))
                return "aigu";
        return "medium";
    }

    public String getDureeNote(String note) {
        if(note.matches(".{1,3}/"))
            return "croche";
        if (note.matches(".{1,3}1"))
            return "noire";
        if (note.matches(".{1,3}2"))
            return "blanche";
        if (note.matches(".{1,3}4"))
            return "ronde";
        return null;
    }

    public String getDureeSilence(String note) {
        String dureeSilence = getDureeNote(note);
        System.out.println(dureeSilence);
        switch (dureeSilence){
            case "croche":
                System.out.println("ds");
                return "demiSoupir";
            case "noire":
                System.out.println("s");
                return "soupir";
            case "blanche":
                System.out.println("dp");
                return "demiPause";
            case "ronde":
                System.out.println("p");
                return "pause";
        }
        return note;
    }

    public String getMelodie() {
        return melodie;
    }

    public Boolean getNouveauProjet() {
        return nouveauProjet; }

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
