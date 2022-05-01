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
        partitio.setMelodie("C");
    }

    @FXML
    public void jouerLaDiese(){
        partitio.play("^C");
        partitio.setMelodie("^C");
    }

    @FXML
    public void jouerSi(){
        partitio.play("D");
        partitio.setMelodie("D");
    }

    @FXML
    public void jouerDo(){
        partitio.play("E");
        partitio.setMelodie("E");
    }

    @FXML
    public void jouerDoDiese(){
        partitio.play("^E");
        partitio.setMelodie("^E");
    }

    @FXML
    public void jouerRe(){
        partitio.play("F");
        partitio.setMelodie("F");
    }

    @FXML
    public void jouerReDiese(){
        partitio.play("^F");
        partitio.setMelodie("^F");
    }

    @FXML
    public void jouerMi(){
        partitio.play("G");
        partitio.setMelodie("G");
    }

    @FXML
    public void jouerFa(){
        partitio.play("A");
        partitio.setMelodie("A");
    }

    @FXML
    public void jouerFaDiese(){
        partitio.play("^A");
        partitio.setMelodie("^A");
    }

    @FXML
    public void jouerSol(){
        partitio.play("B");
        partitio.setMelodie("B");
    }

    @FXML
    public void jouerSolDiese(){
        partitio.play("^B");
        partitio.setMelodie("^B");
    }

    @FXML
    public void jouerSilence(){
        partitio.setMelodie("z2");
    }
}
