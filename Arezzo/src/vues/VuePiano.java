package vues;

import javafx.fxml.FXML;
import partition.Partition;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class VuePiano {
    private Synthesizer synthesizer;
    private Partition partitio;

    public VuePiano(){
        try {
            synthesizer = MidiSystem.getSynthesizer();
            partitio = new Partition(synthesizer);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void jouerLa(){
        partitio.play("C");
    }

    @FXML
    public void jouerLaDiese(){
        partitio.play("Do");
    }

    @FXML
    public void jouerSi(){
        partitio.play("D");
    }

    @FXML
    public void jouerDo(){
        partitio.play("E");
        System.out.println("test");
    }

    @FXML
    public void jouerDoDiese(){
        partitio.play("Do");
    }

    @FXML
    public void jouerRe(){
        partitio.play("F");
    }

    @FXML
    public void jouerReDiese(){
        partitio.play("Do");
    }

    @FXML
    public void jouerMi(){
        partitio.play("G");
    }

    @FXML
    public void jouerFa(){
        partitio.play("A");
    }

    @FXML
    public void jouerFaDiese(){
        partitio.play("Do");
    }

    @FXML
    public void jouerSol(){
        partitio.play("B");
    }

    @FXML
    public void jouerSolDiese(){
        partitio.play("Do");
    }





}
