package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.Arezzo;
import partition.Partition;

import javax.swing.text.html.StyleSheet;

public class EcouteurInstruments implements Observateur{
    @FXML
    private ChoiceBox<String> boxInstruments;
    @FXML
    private ObservableList<String> listInstruments;
    @FXML
    private RadioButton croche,medium;
    @FXML
    private Slider tempo,volume;
    @FXML
    ToggleGroup groupHauteurs, groupDuree;
    @FXML
    private AnchorPane fenetreInstruments;
    private Arezzo arezzo;
    private Partition par;

    public EcouteurInstruments(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
        par = this.arezzo.getPartition();
    }

    @FXML
    public void initialize(){
        //Liste instruments
        boxInstruments.setItems(listInstruments);
        boxInstruments.setValue("Piano");
        boxInstruments.setStyle("-fx-background-color: #B6E2D3");
        fenetreInstruments.setStyle("-fx-background-color: #B6E2D3;");
        tempo.setStyle("-fx-control-inner-background: #FAE8E0;");
        volume.setStyle("-fx-control-inner-background: #FAE8E0;");
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
        //Permet d'actualiser l'instrument choisit actuellement
        par.setInstrument(boxInstruments.getValue());

        //Actualise la valeur du tempo sur le slider (quand on ouvre un fichier par exemple)
        tempo.setValue(arezzo.getTempo());

        //Si on fait un nouveau projet, on remet les paramètres par défaut
        if(arezzo.getNouveauProjet()){
            reInitialize();
        }
    }
}
