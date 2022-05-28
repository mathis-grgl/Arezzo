package model;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import partition.Partition;

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
        //Initialisation du synthesizer
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        //Initialisation de la partition
        partition = new Partition(synthesizer);
        partition.setTitre("");
        partition.setMelodie("");
        partition.setPreferedMaxWidth(800);

        //Initialisation des variables du model
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
        //Réinitialise le piano comme instrument
        partition.setInstrument("Piano");

        //Réinitialise la mélodie de la partition comme vide
        partition.setMelodie("");

        //Réinitialise la liste des notes comme vide
        listMelodie = new ArrayList<>();

        //Réinitialise la mélodie comme vide
        melodie = "";

        //Réinitialise le titre comme "Nouveau projet"
        titre = "Nouveau projet";

        //Réinitialise le tempo à 180
        tempo = 180.0;

        //Réinitialise la durée à croche
        duree = "croche";

        //Réinitialise l'octave à medium
        hauteur = "medium";

        //Réinitialise le tempo de la partition à 180
        partition.setTempo(tempo.intValue());

        //Réinitialise le temps à 1 (utile pour les barres de mesure dans la mélodie)
        temps = 1.0;

        //Définit le projet comme nouveau (utile pour la réinitialisation de l'affichage)
        nouveauProjet = true;

        //Bug sur certains pc de la faculté qui crée une erreur (dû à des problèmes de son)
        //partition.setVolume(80);
    }

    /**
     * Supprime la mélodie.
     */
    public void deleteMelodie(){
        //Joue une mélodie vide pour stopper une éventuelle mélodie en train de jouer
        partition.play("");

        //Définit la mélodie de la partition comme vide
        partition.setMelodie("");

        //Supprime toutes les notes de la liste des notes
        listMelodie.clear();

        //Définit la mélodie comme vide
        melodie = "";

        //Définit le temps à 1 comme si c'était un nouveau projet
        temps = 1.0;
    }

    /**
     * Ajoute une note à la mélodie.
     * @param lettre La note
     */
    public void addMelodie(String lettre){
        //Ajoute la note à la liste des notes
        listMelodie.add(lettre);

        //Permet d'ajouter au bon moment les barres de mesure
        if(temps >= 4) {
            temps = 0.0;
            listMelodie.add("|");
        }

        //Convertit la liste des notes en mélodie
        convertirListEnMelodie();
    }

    /**
     * Convertit la liste de notes en mélodie.
     */
    public void convertirListEnMelodie(){

        //Déclaration et initialisation du StringBuilder qui permet de reconstruire la mélodie depuis la liste des notes
        StringBuilder concat = new StringBuilder();

        //Pour toutes les notes dans la liste des notes
        for (String lettre : listMelodie) {
            //On ajoute la note puis un espace
            concat.append(lettre);
            concat.append(" ");
        }

        //On définit la mélodie depuis le StringBuilder construit précédemment
        melodie = concat.toString();

        //Redéfinit la mélodie dans la partition et vérifie que tout soit synchroniser
        setMelodie(melodie);
    }

    /**
     * Convertit la mélodie en liste de notes.
     */
    public void convertirMelodieEnList(){
        //Sépare la mélodie en un tableau de notes
        String[] separation = melodie.split("\\s");

        //Convertit le tableau en liste de notes
        List<String> listCopie = Arrays.asList(separation);

        //Réinitialise la liste des notes
        listMelodie = new ArrayList<>();

        //Ajoute la liste temporaire dans la liste des notes
        listMelodie.addAll(listCopie);
    }

    /**
     * Joue la mélodie en entier.
     */
    public void jouerMelodie() {
        //Convertit la liste des notes en une mélodie
        convertirListEnMelodie();

        //Joue la mélodie en entier
        partition.play(melodie);

        //Définit la mélodie dans la partition
        partition.setMelodie(melodie);
    }

    /**
     * Modifie la mélodie en fonction de la mélodie en paramètre.
     * @param melodie La nouvelle mélodie
     */
    public void setMelodie(String melodie) {
        //Définit la mélodie en fonction de la mélodie en paramètre
        this.melodie = melodie;

        //Définit la mélodie de la partion avec la nouvelle mélodie
        partition.setMelodie(melodie);

        //Convertit la mélodie en liste de notes
        convertirMelodieEnList();
    }

    /**
     * Joue une note vide (utile pour stopper une mélodie qui est en train de jouer).
     */
    public void playMelodieVide(){
        //Joue une note vide
        partition.play("");
    }

    /**
     * Permet de retourner et d'ajouter une note à la mélodie avec ces spécificités (octave et durée).
     * @param lettre La note vierge
     * @return La note complète
     */
    public String getNotationHauteurDuree(String lettre) {

        //Copie le String en paramètre
        String lettreABC = lettre;

        //Initialise le StringBuilder qui va concaténer une note au complet (avec octave et durée)
        StringBuilder concatenation = new StringBuilder();

        //Si la lettre est une note autre qu'un silence ou une barre de mesure
        if (!lettre.equals("z") && !lettre.equals("|")) {

            switch (hauteur) {

                case "aigu":
                    //Ajoute la note en minuscule si l'octave est aigu
                    lettreABC = lettreABC.toLowerCase();
                    concatenation.append(lettreABC);
                    break;

                case "grave":
                    //Ajoute la note en majuscule avec une virgule si l'octave est grave
                    concatenation.append(lettreABC);
                    concatenation.append(",");
                    break;

                case "medium":
                    //Ajoute la note en majuscule si l'octave est medium
                    concatenation.append(lettreABC);
                    break;
            }
        }

        else {
            if(lettre.equals("z"))
                //Ajoute un silence si la note est un silence
                concatenation.append("z");
        }

        switch (duree) {

            case "croche":
                //Ajoute un / si la durée est une croche
                concatenation.append("/");
                temps += 0.5;
                break;

            case "ronde":
                //Ajoute un 4 si la durée est une ronde
                concatenation.append("4");
                temps += 4;
                break;

            case "blanche":
                //Ajoute un 2 si la durée est une blanche
                concatenation.append("2");
                temps += 2;
                break;

            case "noire":
                //Ajoute un 1 si la durée est une noire
                concatenation.append("1");
                temps += 1;
                break;
        }

        //Ajoute la note complète(StringBuilder) à la mélodie
        addMelodie(concatenation.toString());

        //Définit la mélodie de la partition en fonction de la mélodie qui vient d'être modifié
        partition.setMelodie(melodie);

        //Retourne la note complète
        return concatenation.toString();
    }

    /**
     * Transpose toute la mélodie en fonction de l'entier rentré en paramètre.
     * @param nbTransposition Le nombre de demi-tons à transposer
     */
    public void transposerNotesArezzo(int nbTransposition){

        for (int i = 0; i < listMelodie.size(); i++) {
            //Pour toutes les notes dans la liste des Notes, on transpose la mélodie en fontion de l'entier en paramètre
            transposerNote(nbTransposition, i);
        }

        //Convertit la liste des notes en mélodie
        convertirListEnMelodie();
    }

    /**
     * Retourne une note vierge depuis une note complète rentrée en paramètre.
     * @param note La note complète (^C,2)
     * @return La note vierge (^C)
     */
    public String noteSansSurPlusMajuscule(String note){

        //Copie temporaire de la note en paramètre
        String noteSP = note;

        //Définit charSP comme le caractère de fin de la note (qui correspond à la durée ici)
        String charSP = String.valueOf(noteSP.charAt(noteSP.length()-1));

        //Vérifie que charSP correspond bien à la durée et on le soustrait à la note si c'est bien le cas
        if(charSP.equals("/") || charSP.equals("1") || charSP.equals("2") || charSP.equals("4")) {
            noteSP = noteSP.replace(charSP,"");
        }

        //Définit charSP comme le caractère de fin de la note (qui correspond à l'octave ici)
        charSP = String.valueOf(noteSP.charAt(noteSP.length()-1));

        //Vérifie que charSP correspond bien à l'octave et on le soustrait à la note si c'est bien le cas
        if(charSP.equals(","))
            noteSP = noteSP.replace(charSP,"");

        //Définit la note qu'elle soit forcément en majuscule (pour éviter des bugs)
        noteSP = noteSP.toUpperCase();

        //Retourne la note sans octave ni durée
        return noteSP;
    }

    /**
     * Convertit une note classique en sa version ABC.
     * @param note La note classique (Do)
     * @return La note version ABC (C)
     */
    public String conversionNotesVersABC(String note) {

        /* Les index des notes classiques et ABC se correspondent
        donc cette boucle avec le if permet de trouver le bon index */
        for (int i = 0; i < listNotes.size(); i++) {
            if(listNotes.get(i).equals(note)){

                //Retourne la note ABC qui correspond à la note classique
                return listNotesABC.get(i);
            }
        }

        //Retourne null si aucune correspondance
        return null;
    }

    /**
     * Convertit une note ABC en sa version classique.
     * @param note La note version ABC (C)
     * @return La note classique (Do)
     */
    public String conversionNotesVersClassique(String note) {

        /* Les index des notes classiques et ABC se correspondent
        donc cette boucle avec le if permet de trouver le bon index */
        for (int i = 0; i < listNotesABC.size(); i++) {
            if(listNotesABC.get(i).equals(note)){

                //Retourne la note classique qui correspond à la note ABC
                return listNotes.get(i);
            }
        }

        //Retourne null si aucune correspondance
        return null;
    }

    /**
     * Supprime les notes sélectionnées dans la liste de notes en fonction des index en paramètres.
     * @param notes Les index des notes sélectionnées
     */
    public void supprimerNote(ObservableList<Integer> notes){

        //Pour tous les index correspondant aux notes sélectionnées
        for(int index : notes){

            //Vérifie que la suppression des notes laisse une note dans la mélodie
            if(listMelodie.size()>1)

                //Supprime la note dans la liste des notes
                listMelodie.remove(index);
        }

        //Convertit la liste des notes en mélodie
        convertirListEnMelodie();
    }

    /**
     * Transpose les notes sélectionnées dans la liste de notes en fonction des index en paramètres.
     * @param nbTransposition Le nombre de demi-tons à transposer
     * @param notes Les index des notes sélectionnées
     * @see #transposerNote(int, int)
     */
    public void transposerNoteComposition(int nbTransposition, ObservableList<Integer> notes){

        //Pour tous les index correspondant aux notes sélectionnées
        for (int index : notes)

            //Transpose les notes en fonction de l'entier en paramètre
            transposerNote(nbTransposition, index);

        //Convertit la liste des notes en mélodie
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

        //Soustrait la durée de la note
        String noteSansDuree = note.substring(0,note.length()-1);

        //Déclare et initialise checkLettre comme étant l'octave
        Character checkLettre = note.charAt(noteSansDuree.length()-1);

        //Si le caractère est ",", retourne "grave"
        if(String.valueOf(checkLettre).equals(","))
            return "grave";

        //Si le caractère est une lettre et est en minuscule, retourne "aigu"
        if(Character.isLowerCase(checkLettre) && Character.isLetter(checkLettre))
                return "aigu";

        //Sinon retourne "medium"
        return "medium";
    }

    /**
     * Retourne la durée d'une note.
     * @param note La note
     * @return La durée de la note
     */
    public String getDureeNote(String note) {

        //Si la note correspond au regex suivant (se terminant par un /), retourne "croche"
        if(note.matches(".{1,3}/"))
            return "croche";

        //Si la note correspond au regex suivant (se terminant par un 1), retourne "noire"
        if (note.matches(".{1,3}1"))
            return "noire";

        //Si la note correspond au regex suivant (se terminant par un 2), retourne "blanche"
        if (note.matches(".{1,3}2"))
            return "blanche";

        //Si la note correspond au regex suivant (se terminant par un 4), retourne "ronde"
        if (note.matches(".{1,3}4"))
            return "ronde";

        //Sinon retourne null
        return null;
    }

    /**
     * Retourne la durée d'un silence.
     * @param note Le silence
     * @return La durée du silence
     * @see #getDureeNote(String) 
     */
    public String getDureeSilence(String note) {

        //Durée de la note en paramètre
        String dureeSilence = getDureeNote(note);

        switch (dureeSilence){

            case "croche":
                //Si la durée de la note est une croche, retourne son équivalent "demiSoupir"
                return "demiSoupir";

            case "noire":
                //Si la durée de la note est une noire, retourne son équivalent "soupir"
                return "soupir";

            case "blanche":
                //Si la durée de la note est une blanche, retourne son équivalent "demiPause"
                return "demiPause";

            case "ronde":
                //Si la durée de la note est une ronde, retourne son équivalent "pause"
                return "pause";
        }

        //Sinon retourne la durée sans équivalent
        return dureeSilence;
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

        //Définit le tempo de la partition en fonction du tempo du model
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
