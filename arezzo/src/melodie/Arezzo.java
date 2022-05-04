package melodie;

import partition.Partition;
import vues.SujetObserve;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Arezzo extends SujetObserve {
    private String instruments, octave, hauteur;
    private Synthesizer synthesizer;
    private Partition partition;

    public Arezzo(){
        try {
            synthesizer = MidiSystem.getSynthesizer();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        partition = new Partition(synthesizer);

    }
}
