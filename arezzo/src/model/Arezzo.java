package model;

import partition.Partition;
import vues.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Arezzo extends SujetObserve {
    private Synthesizer synthesizer;
    private Partition partition;
    private Boolean nouveauProjet;

    public Arezzo(){
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        partition = new Partition(synthesizer);
        nouveauProjet = false;
    }

    public Partition getPartition() {
        return partition;
    }

    public void resetAll(){
        partition.setInstrument("Piano");
        partition.setMelodie("");
        partition.setTempo(180);
        partition.setTitre("AHAHAHHAH");
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
}
