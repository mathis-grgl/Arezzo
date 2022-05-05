package model;

import partition.Partition;
import vues.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Arezzo extends SujetObserve {
    private Boolean aigu,medium,grave,croche,ronde,blanche,noire,nouveauProjet;
    private Synthesizer synthesizer;
    private Partition partition;
    private String melodie;

    public Arezzo(){
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        partition = new Partition(synthesizer);
        nouveauProjet = false;
        aigu = false;
        medium = true;
        grave = false;
    }

    public Partition getPartition() {
        return partition;
    }

    public void resetAll(){
        partition.setInstrument("Piano");
        partition.setMelodie("");
        partition.setTempo(180);
        partition.setTitre("Nouveau Projet");
        partition.setVolume(50);
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
        if(String.valueOf(aigu).equals(hauteur)){
            aigu = true;
            medium = false;
            grave = false;
        }
        if(String.valueOf(medium).equals(hauteur)){
            aigu = false;
            medium = true;
            grave = false;
        }
        if(String.valueOf(grave).equals(hauteur)){
            aigu = false;
            medium = false;
            grave = true;
        }
    }

    public void setDuree(String duree){
        if(String.valueOf(croche).equals(duree)){
            croche = true;
            ronde = false;
            blanche = false;
            noire = false;
        }
        if(String.valueOf(ronde).equals(duree)){
            croche = false;
            ronde = true;
            blanche = false;
            noire = false;
        }
        if(String.valueOf(blanche).equals(duree)){
            croche = false;
            ronde = false;
            blanche = true;
            noire = false;
        }
        if(String.valueOf(noire).equals(duree)){
            croche = false;
            ronde = false;
            blanche = false;
            noire = true;
        }
    }

    public String getHauteur(){
        if(aigu) return String.valueOf(aigu);
        if(medium) return String.valueOf(medium);
        if(grave) return String.valueOf(grave);
        return null;
    }

    public String getDuree(){
        if(croche) return String.valueOf(croche);
        if(ronde) return String.valueOf(ronde);
        if(blanche) return String.valueOf(blanche);
        if(noire) return String.valueOf(noire);
        return null;
    }
}
