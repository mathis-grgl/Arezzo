package vues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import melodie.Arezzo;
import partition.Partition;

public class VueInstruments implements Observateur{
    @FXML
    private ChoiceBox<String> boxInstruments;
    @FXML
    private ObservableList<String> listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
    @FXML
    private RadioButton guitare,piano,saxophone, trompette;

    private Arezzo arezzo;

    public VueInstruments(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    public void changerInstruments(){
        par.setInstrument(boxInstruments.getValue());
    }
    
    @FXML
    public void initialize(){
        boxInstruments.setItems(listInstruments);
        boxInstruments.setValue("Piano");
        /*ToggleGroup listInstruments = new ToggleGroup();
        guitare.setToggleGroup(listInstruments);
        piano.setToggleGroup(listInstruments);
        saxophone.setToggleGroup(listInstruments);
        trompette.setToggleGroup(listInstruments);*/
    }

    @Override
    public void reagir() {
        
    }
}
