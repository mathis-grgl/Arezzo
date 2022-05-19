package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import model.Arezzo;
import partition.Partition;

public class EcouteurInstruments implements Observateur{
    @FXML
    private ChoiceBox<String> boxInstruments;
    @FXML
    private ObservableList<String> listInstruments;
    @FXML
    private RadioButton croche,noire,blanche, ronde,aigu,medium,grave;
    @FXML
    private Slider tempo,volume;
    @FXML
    ToggleGroup groupHauteurs, groupDuree;

    private Arezzo arezzo;
    private Partition par;

    public EcouteurInstruments(Arezzo arezzo){
        this.arezzo = arezzo;
        listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();
    }

    @FXML
    public void initialize(){
        //Liste instruments
        boxInstruments.setItems(listInstruments);
        boxInstruments.setValue("Piano");
    }


    @FXML
    public void setHauteur(){
        arezzo.setHauteur(((RadioButton) groupHauteurs.getSelectedToggle()).getId());
        arezzo.notifierObservateur();
    }

    @FXML
    public void setDuree(){
        arezzo.setDuree(((RadioButton) groupDuree.getSelectedToggle()).getId());
        arezzo.notifierObservateur();
    }

    @FXML
    public void setTempoSlider(){
        arezzo.setTempo(tempo.getValue());
        par.setTempo((int) tempo.getValue());
        arezzo.notifierObservateur();
    }

    @FXML
    public void setVolume() {
        par.setVolume(volume.getValue());
        arezzo.notifierObservateur();
    }

    public void reInitialize(){
        croche.setSelected(true);
        medium.setSelected(true);
        boxInstruments.setValue("Piano");
        volume.setValue(80);
        tempo.setValue(180);
        arezzo.setNouveauProjet(false);
    }

    @Override
    public void reagir() {
        par.setInstrument(boxInstruments.getValue());
        tempo.setValue(arezzo.getTempo());

        //Nouveau projet
        if(arezzo.getNouveauProjet()){
            reInitialize();
        }
    }
}
