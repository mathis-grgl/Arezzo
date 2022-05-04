package vues;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import partition.Partition;

public class VueInstruments {
    private Partition par;

    @FXML
    private RadioButton guitare,piano,saxophone, trompette;

    public VueInstruments(Partition par){
        this.par = par;
    }

    @FXML
    public void changerPiano(){
        par.setInstrument("Piano");
    }

    @FXML
    public void changerGuitare(){
        par.setInstrument("Guitare");
    }

    @FXML
    public void changerSaxophone(){
        par.setInstrument("Saxophone");
    }

    @FXML
    public void changerTrompette(){
        par.setInstrument("Trompette");
    }

    @FXML
    public void initialize(){
        ToggleGroup listInstruments = new ToggleGroup();
        
        guitare.setToggleGroup(listInstruments);
        piano.setToggleGroup(listInstruments);
        saxophone.setToggleGroup(listInstruments);
        trompette.setToggleGroup(listInstruments);
    }

}
