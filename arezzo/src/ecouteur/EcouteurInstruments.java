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

/**
 * L'écouteur lié à la VueInstruments.
 */
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

    /**
     * Instancie une nouvelle instance de EcouteurInstruments.
     * @param arezzo Le model Arezzo
     */
    public EcouteurInstruments(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        par = this.arezzo.getPartition();

        listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
    }

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
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


    /**
     * Définit l'octave de la note depuis les différents boutons d'octaves.
     */
    @FXML
    public void setHauteur(){
        arezzo.setHauteur(((RadioButton) groupHauteurs.getSelectedToggle()).getId());
        arezzo.notifierObservateurs();
    }

    /**
     * Définit la durée de la note depuis les différents boutons de durée de notes.
     */
    @FXML
    public void setDuree(){
        arezzo.setDuree(((RadioButton) groupDuree.getSelectedToggle()).getId());
        arezzo.notifierObservateurs();
    }

    /**
     * Définit le tempo de la partition depuis le curseur.
     */
    @FXML
    public void setTempoSlider(){
        arezzo.setTempo(tempo.getValue());
        arezzo.notifierObservateurs();
    }

    /**
     * Définit le volume du pc depuis le curseur (linux uniquement).
     */
    @FXML
    public void setVolume() {
        par.setVolume(volume.getValue());
        arezzo.notifierObservateurs();
    }

    /**
     * Remet tous les boutons et curseurs à leur valeur par défaut.
     */
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
