package model;

import partition.Partition;
import ecouteur.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Arezzo extends SujetObserve {
    private Boolean nouveauProjet;
    private Synthesizer synthesizer;
    private Partition partition;
    private String melodie, hauteur,duree;

    public Arezzo(){
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        partition = new Partition(synthesizer);

        nouveauProjet = false;

        hauteur = "medium";
        duree = "croche";
        melodie = "";

        partition.setVolume(80);
    }

    public Partition getPartition() {
        return partition;
    }

    public void resetAll(){
        partition.setInstrument("Piano");
        partition.setMelodie("");
        partition.setTempo(180);
        partition.setTitre("Nouveau Projet");
        partition.setVolume(80);
        nouveauProjet = true;
        this.notifierObservateur();
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

    public String getHauteur(){
        return hauteur;
    }

    public String getDuree(){
        return duree;
    }

    public void addMelodie(String lettre){
        StringBuilder ajoutLettre = new StringBuilder();
        ajoutLettre.append(melodie);
        ajoutLettre.append(" ");
        ajoutLettre.append(lettre);
        melodie = ajoutLettre.toString();
        System.out.println(melodie);
    }
}
