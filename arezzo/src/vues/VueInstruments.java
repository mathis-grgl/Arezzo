package vues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import model.Arezzo;
import partition.Partition;

public class VueInstruments implements Observateur{
    @FXML
    private ChoiceBox<String> boxInstruments;
    @FXML
    private ObservableList<String> listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");

    private Arezzo arezzo;
    private Partition par;

    public VueInstruments(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();
    }
    
    @FXML
    public void initialize(){
        boxInstruments.setItems(listInstruments);
        boxInstruments.setValue("Piano");
    }

    @Override
    public void reagir() {
        par.setInstrument(boxInstruments.getValue());
    }
}
