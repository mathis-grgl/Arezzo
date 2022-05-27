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
     * Instancie un Arezzo.
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
     * Remet par défaut toutes les valeurs possibles (concernant la mélodie).
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

        //Bug sur certains pc de la faculté qui crée une erreur (dû à des problèmes de son)
        //partition.setVolume(80);
    }

    /**
     * Supprime la mélodie.
     */
    public void deleteMelodie(){
        partition.play("");
        partition.setMelodie("");

        listMelodie.clear();
        melodie = "";

        temps = 1.0;
    }

    /**
     * Ajoute une note à la mélodie.
     * @param lettre La note
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
     * Convertit la liste de notes en mélodie.
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
     * Convertit la mélodie en liste de notes.
     */
    public void convertirMelodieEnList(){
        String[] separation = melodie.split("\\s");
        List<String> listCopie = Arrays.asList(separation);

        listMelodie = new ArrayList<>();
        listMelodie.addAll(listCopie);
    }

    /**
     * Joue la mélodie en entier.
     */
    public void jouerMelodie() {
        convertirListEnMelodie();

        partition.play(melodie);
        partition.setMelodie(melodie);
    }

    /**
     * Modifie la mélodie en fonction de la mélodie en paramètre.
     * @param melodie La nouvelle mélodie
     */
    public void setMelodie(String melodie) {
        this.melodie = melodie;

        convertirMelodieEnList();

        partition.setMelodie(melodie);
    }

    /**
     * Joue une note vide (utile pour stopper une mélodie qui est en train de jouer).
     */
    public void playMelodieVide(){
        partition.play("");
    }

    /**
     * Permet de retourner et d'ajouter une note à la mélodie avec ces spécificités (octave et durée).
     * @param lettre La note vierge
     * @return La note complète
     */
    public String getNotationHauteurDuree(String lettre) {

        String lettreABC = lettre;

        StringBuilder concatenation = new StringBuilder();

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
        }

        else {
            if(lettre.equals("z"))
                concatenation.append("z");
        }

        switch (duree) {
            case "croche":
                concatenation.append("/");
                temps += 0.5;
                break;
            case "ronde":
                concatenation.append("4");
                temps += 4;
                break;
            case "blanche":
                concatenation.append("2");
                temps += 2;
                break;
            case "noire":
                concatenation.append("1");
                temps += 1;
                break;
        }

        addMelodie(concatenation.toString());

        partition.setMelodie(melodie);

        return concatenation.toString();
    }

    /**
     * Transpose toute la mélodie en fonction de l'entier rentré en paramètre.
     * @param nbTransposition Le nombre de demi-tons à transposer
     */
    public void transposerNotesArezzo(int nbTransposition){

        for (int i = 0; i < listMelodie.size(); i++) {
            transposerNote(nbTransposition, i);
        }

        convertirListEnMelodie();
    }

    /**
     * Retourne une note vierge depuis une note complète rentrée en paramètre.
     * @param note La note complète (^C,2)
     * @return La note vierge (^C)
     */
    public String noteSansSurPlusMajuscule(String note){

        String noteSP = note;

        String charSP = String.valueOf(noteSP.charAt(noteSP.length()-1));

        if(charSP.equals("/") || charSP.equals("1") || charSP.equals("2") || charSP.equals("4")) {
            noteSP = noteSP.replace(charSP,"");
        }

        charSP = String.valueOf(noteSP.charAt(noteSP.length()-1));

        if(charSP.equals(","))
            noteSP = noteSP.replace(charSP,"");

        noteSP = noteSP.toUpperCase();

        return noteSP;
    }

    /**
     * Convertit une note classique en sa version ABC.
     * @param note La note classique (Do)
     * @return La note version ABC (C)
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
     * Convertit une note ABC en sa version classique.
     * @param note La note version ABC (C)
     * @return La note classique (Do)
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
     * Supprime les notes sélectionnées dans la liste de notes en fonction des index en paramètres.
     * @param notes Les index des notes sélectionnées
     */
    public void supprimerNote(ObservableList<Integer> notes){

        for(int index : notes){
            if(listMelodie.size()>1)
                listMelodie.remove(index);
        }

        convertirListEnMelodie();
    }

    /**
     * Transpose les notes sélectionnées dans la liste de notes en fonction des index en paramètres.
     * @param nbTransposition Le nombre de demi-tons à transposer
     * @param notes Les index des notes sélectionnées
     * @see #transposerNote(int, int)
     */
    public void transposerNoteComposition(int nbTransposition, ObservableList<Integer> notes){

        for (int index : notes) {
            transposerNote(nbTransposition, index);
        }

        convertirListEnMelodie();
    }

    /**
     * Sous fonction qui permet de factoriser le code de transposition.
     * @param nbTransposition Le nombre de demi-tons à transposer
     * @param index Index de la note à transposer
     */
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
     * Permet de savoir si une note est un silence.
     * @param note La note
     * @return Vrai si est un silence
     */
    public boolean noteEstUnSilence(String note){
        return noteSansSurPlusMajuscule(note).equals("Z");
    }

    /**
     * Retourne l'octave d'une note.
     * @param note La note
     * @return L'octave de la note
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
     * Retourne la durée d'une note.
     * @param note La note
     * @return La durée de la note
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
     * Retourne la durée d'un silence.
     * @param note Le silence
     * @return La durée du silence
     * @see #getDureeNote(String) 
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
     * Retourne la mélodie.
     * @return La mélodie en chaîne de caractère
     */
    public String getMelodie() {
        return melodie;
    }

    /**
     * Permet de savoir si la mélodie est un nouveau projet (utile pour réinitialiser la partie graphique).
     * @return Vrai si c'est un nouveau projet
     */
    public Boolean getNouveauProjet() {
        return nouveauProjet;
    }

    /**
     * Modifie la valeur de nouveauProjet en fonction du booléen en paramètre.
     * @param nouveauProjet La nouvelle valeur booléenne
     */
    public void setNouveauProjet(Boolean nouveauProjet) {
        this.nouveauProjet = nouveauProjet;
    }

    /**
     * Définit l'octave pour les notes qui vont être ajoutées.
     * @param hauteur Le nouvel octave
     */
    public void setHauteur(String hauteur){
        this.hauteur = hauteur;
    }

    /**
     * Définit la durée pour les notes qui vont être ajoutées.
     * @param duree La nouvelle durée
     */
    public void setDuree(String duree){
        this.duree = duree;
    }

    /**
     * Définit le tempo pour la mélodie.
     * @param tempo Le nouveau tempo
     */
    public void setTempo(Double tempo) {
        this.tempo = tempo;
        partition.setTempo(tempo.intValue());
    }

    /**
     * Retourne le titre de la mélodie.
     * @return Le titre de la mélodie
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Modifie le titre de la mélodie en fonction du titre en paramètre.
     * @param titre Le nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne la partition initialisé dans Arezzo.
     * @return La partition actuelle
     */
    public Partition getPartition() {
        return partition;
    }

    /**
     * Ferme la partition (utile pour tout quitter).
     */
    public void fermerPartition(){
        partition.close();
    }

    /**
     * Retourne l'image contenant la mélodie.
     * @return L'image de la mélodie
     */
    public Image getImage(){
        return partition.getImage();
    }

    /**
     * Retourne le tempo actuel.
     * @return Le tempo
     */
    public int getTempo() {
        return tempo.intValue();
    }

    /**
     * Retourne une note dans la liste des notes en fonction de l'index en paramètre.
     * @param index L'index de la note
     * @return La note
     */
    public String getNoteMelodie(int index){
        return listMelodie.get(index);
    }

    /**
     * Retourne la liste des notes.
     * @return La liste des notes.
     */
    public ArrayList<String> getListMelodie() {
        return listMelodie;
    }

    /**
     * Considère qu'une mélodie ne peut pas descendre en dessous d'une note.
     * @return Vrai s'il reste une note dans la liste de notes.
     */
    public boolean nePeutPlusEtreSupprimer(){
        return listMelodie.size() == 1;
    }

    /**
     * Change la couleur d'une note dans la liste des notes en fonction de l'index en paramètre et d'une chaîne de caractères correspondant à la couleur souhaitée (en anglais).
     * @param index L'index de la note
     * @param color La couleur souhaitée
     */
    public void changerCouleurNote(int index, String color){
        partition.setCouleurs(Color.valueOf(color),index);
    }

    /**
     * Retourne vrai si la liste des notes est vide.
     * @return Vrai si est vide
     */
    public boolean estVide(){
        return listMelodie.isEmpty();
    }
}
