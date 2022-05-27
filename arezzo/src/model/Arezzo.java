package model;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import partition.Partition;
import ecouteur.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Le modèle Arezzo qui contient toutes les fonctions concernant la gestion des données (partition, notes, mélodie).
 */
public class Arezzo extends SujetObserve {
    private Boolean nouveauProjet;
    private Synthesizer synthesizer;
    private Partition partition;
    private String melodie, hauteur,duree,titre;
    private ArrayList<String> listMelodie;
    private Double temps,tempo;
    private List<String> listNotesABC, listNotes, listNotesWOctaves, listSilence;

    /**
     * Instantiates a new Arezzo.
     */
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
        temps = 0.0;
        tempo = 180.0;

        //Initialisation de la liste des notes en version ABC
        listNotesABC = List.of("C","^C","D","^D","E","F","^F","G","^G","A","^A","B","Z");

        //Initialisation de la liste des notes en version classique
        listNotes = List.of("Do","DoDiese","Re","ReDiese","Mi","Fa","FaDiese","Sol","SolDiese","La","LaDiese","Si","Silence");

        //Initialisation de la liste des notes en version octaves
        listNotesWOctaves = List.of("C,","^C,","D,","^D,","E,","F,","^F,","G,","^G,","A,","^A,","B,","C","^C","D","^D","E","F","^F","G","^G","A","^A","B","c","^c","d","^d","e","f","^f","g","^g","a","^a","b");

        //Initialisation de la liste des silences
        listSilence = List.of("z/","z1","z2","z4");

        //Initialisation de la liste contenant la mélodie
        listMelodie = new ArrayList<>();

        //Bug sur certains pc de la faculté qui empêche le lancement (dû à des problèmes de son)
        //partition.setVolume(80);
    }

    /**
     * Reset all.
     */
    public void resetAll(){
        partition.setInstrument("Piano");
        partition.setMelodie("");
        listMelodie = new ArrayList<>();
        melodie = "";
        titre = "Nouveau projet";
        tempo = 180.0;
        partition.setTempo(tempo.intValue());
        temps = 1.0;
        nouveauProjet = true;

        //Bug sur certains pc de la faculté qui empêche le lancement (dû à des problèmes de son)
        //partition.setVolume(80);
    }

    /**
     * Delete melodie.
     */
    public void deleteMelodie(){
        partition.play("");
        partition.setMelodie("");
        listMelodie.clear();
        melodie = "";
        temps = 1.0;
    }

    /**
     * Add melodie.
     *
     * @param lettre the lettre
     */
    public void addMelodie(String lettre){
        listMelodie.add(lettre);
        if(temps >= 4) {
            temps = 0.0;
            listMelodie.add("|");
        }
        convertirListEnMelodie();
    }

    /**
     * Convertir list en melodie.
     */
    public void convertirListEnMelodie(){
        StringBuilder concat = new StringBuilder();
        for (String lettre : listMelodie) {
            concat.append(lettre);
            concat.append(" ");
        }
        melodie = concat.toString();
        setMelodie(melodie);
    }

    /**
     * Convertir melodie en list.
     */
    public void convertirMelodieEnList(){
        String[] separation = melodie.split("\\s");
        List<String> listCopie = Arrays.asList(separation);
        listMelodie = new ArrayList<>();
        listMelodie.addAll(listCopie);
    }

    /**
     * Jouer melodie.
     */
    public void jouerMelodie() {
        convertirListEnMelodie();
        partition.play(melodie);
        partition.setMelodie(melodie);
    }

    /**
     * Sets melodie.
     *
     * @param melodie the melodie
     */
    public void setMelodie(String melodie) {
        this.melodie = melodie;
        convertirMelodieEnList();
        partition.setMelodie(melodie);
    }

    /**
     * Play melodie vide.
     */
    public void playMelodieVide(){
        partition.play("");
    }

    /**
     * Gets notation hauteur duree.
     *
     * @param lettre the lettre
     * @return the notation hauteur duree
     */
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

    /**
     * Transposer notes arezzo.
     *
     * @param nbTransposition the nb transposition
     */
    public void transposerNotesArezzo(int nbTransposition){
        for (int i = 0; i < listMelodie.size(); i++) {
            transposerNote(nbTransposition, i);
        }
        convertirListEnMelodie();
        notifierObservateur();
    }

    /**
     * Note sans sur plus majuscule string.
     *
     * @param note the note
     * @return the string
     */
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

    /**
     * Conversion notes vers abc string.
     *
     * @param note the note
     * @return the string
     */
    public String conversionNotesVersABC(String note) {
        for (int i = 0; i < listNotes.size(); i++) {
            if(listNotes.get(i).equals(note)){
                return listNotesABC.get(i);
            }
        }
        return null;
    }

    /**
     * Conversion notes vers classique string.
     *
     * @param note the note
     * @return the string
     */
    public String conversionNotesVersClassique(String note) {
        for (int i = 0; i < listNotesABC.size(); i++) {
            if(listNotesABC.get(i).equals(note)){
                return listNotes.get(i);
            }
        }
        return null;
    }

    /**
     * Supprimer note.
     *
     * @param notes the notes
     */
    public void supprimerNote(ObservableList<Integer> notes){
        for(int index : notes){
            if(listMelodie.size()>1)
                listMelodie.remove(index);
        }
        convertirListEnMelodie();
        notifierObservateur();
    }

    /**
     * Transposer note composition.
     *
     * @param nbTransposition the nb transposition
     * @param notes           the notes
     */
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
            //Modifie tout ce qui n'est pas un silence
            if (!noteEstUnSilence(listMelodie.get(index))) {
                //Permet d'extraire les particularités des notes et de faire une liste sans ces particularités
                String symboleDuree = String.valueOf(listMelodie.get(index).charAt(listMelodie.get(index).length() - 1));
                listMelodie.set(index, listMelodie.get(index).replace(symboleDuree, ""));

                //Permet de savoir l'index de la future note
                int indexMelodieListe = listNotesWOctaves.indexOf(listMelodie.get(index));
                int indexTransposition = indexMelodieListe + nbTransposition;
                if (indexTransposition > 35) indexTransposition = 35;
                if (indexTransposition < 0) indexTransposition = 0;

                //Fait les modifications de tons sur la note i
                listMelodie.set(index, listNotesWOctaves.get(indexTransposition) + symboleDuree);

            }
            //Modifie toutes les notes qui sont des silences
            else {

                //Permet de savoir l'index de la future note
                int indexMelodieListe = listSilence.indexOf(listMelodie.get(index));
                int indexTransposition = indexMelodieListe + nbTransposition;
                if (indexTransposition > 3) indexTransposition = 3;
                if (indexTransposition < 0) indexTransposition = 0;

                //Fait les modifications de tons sur la note i
                listMelodie.set(index, listSilence.get(indexTransposition));
            }
        }
    }

    /**
     * Note est un silence boolean.
     *
     * @param note the note
     * @return the boolean
     */
    public boolean noteEstUnSilence(String note){
        return noteSansSurPlusMajuscule(note).equals("Z");
    }

    /**
     * Gets octave note.
     *
     * @param note the note
     * @return the octave note
     */
    public String getOctaveNote(String note) {
        String noteSansDuree = note.substring(0,note.length()-1);
        char checkLettre = note.charAt(noteSansDuree.length()-1);
        if(String.valueOf(checkLettre).equals(","))
            return "grave";
        if(Character.isLowerCase(checkLettre) && Character.isLetter(checkLettre))
                return "aigu";
        return "medium";
    }

    /**
     * Gets duree note.
     *
     * @param note the note
     * @return the duree note
     */
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

    /**
     * Gets duree silence.
     *
     * @param note the note
     * @return the duree silence
     */
    public String getDureeSilence(String note) {
        String dureeSilence = getDureeNote(note);
        switch (dureeSilence){
            case "croche":
                return "demiSoupir";
            case "noire":
                return "soupir";
            case "blanche":
                return "demiPause";
            case "ronde":
                return "pause";
        }
        return note;
    }

    /**
     * Gets melodie.
     *
     * @return the melodie
     */
    public String getMelodie() {
        return melodie;
    }

    /**
     * Gets nouveau projet.
     *
     * @return the nouveau projet
     */
    public Boolean getNouveauProjet() {
        return nouveauProjet; }

    /**
     * Sets nouveau projet.
     *
     * @param nouveauProjet the nouveau projet
     */
    public void setNouveauProjet(Boolean nouveauProjet) {
        this.nouveauProjet = nouveauProjet;
    }

    /**
     * Set hauteur.
     *
     * @param hauteur the hauteur
     */
    public void setHauteur(String hauteur){
        this.hauteur = hauteur;
    }

    /**
     * Set duree.
     *
     * @param duree the duree
     */
    public void setDuree(String duree){
        this.duree = duree;
    }

    /**
     * Sets tempo.
     *
     * @param tempo the tempo
     */
    public void setTempo(Double tempo) {
        this.tempo = tempo;
        partition.setTempo(tempo.intValue());
    }

    /**
     * Gets titre.
     *
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Sets titre.
     *
     * @param titre the titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Gets partition.
     *
     * @return the partition
     */
    public Partition getPartition() {
        return partition;
    }

    /**
     * Fermer partition.
     */
    public void fermerPartition(){
        partition.close();
    }

    /**
     * Get image image.
     *
     * @return the image
     */
    public Image getImage(){
        return partition.getImage();
    }

    /**
     * Gets tempo.
     *
     * @return the tempo
     */
    public int getTempo() {
        return tempo.intValue();
    }

    /**
     * Get note melodie string.
     *
     * @param index the index
     * @return the string
     */
    public String getNoteMelodie(int index){
        return listMelodie.get(index);
    }

    /**
     * Gets list melodie.
     *
     * @return the list melodie
     */
    public ArrayList<String> getListMelodie() {
        return listMelodie;
    }

    /**
     * Ne peut plus etre supprimer boolean.
     *
     * @return the boolean
     */
    public boolean nePeutPlusEtreSupprimer(){
        return listMelodie.size() == 1;
    }

    /**
     * Changer couleur note.
     *
     * @param index the index
     * @param color the color
     */
    public void changerCouleurNote(int index, String color){
        partition.setCouleurs(Color.valueOf(color),index);
    }

    /**
     * Est vide boolean.
     *
     * @return the boolean
     */
    public boolean estVide(){
        return listMelodie.isEmpty();
    }
}
