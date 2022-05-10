package model;

import javafx.scene.control.TextInputDialog;
import partition.Partition;
import ecouteur.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Optional;

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
        if(hauteur.equals("aigu")){
            aigu = true;
            medium = false;
            grave = false;
        }
        if(hauteur.equals("medium")){
            aigu = false;
            medium = true;
            grave = false;
        }
        if(hauteur.equals("grave")){
            aigu = false;
            medium = false;
            grave = true;
        }
    }

    public void setDuree(String duree){
        if(duree.equals("croche")){
            croche = true;
            ronde = false;
            blanche = false;
            noire = false;
        }
        if(duree.equals("ronde")){
            croche = false;
            ronde = true;
            blanche = false;
            noire = false;
        }
        if(duree.equals("blanche")){
            croche = false;
            ronde = false;
            blanche = true;
            noire = false;
        }
        if(duree.equals("noire")){
            croche = false;
            ronde = false;
            blanche = false;
            noire = true;
        }
    }

    public String getHauteur(){
        if(aigu) return "aigu";
        if(medium) return "medium";
        if(grave) return "medium";
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
