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
        //Initialise le model et ajoute l'écouteur en tant qu'Observateur
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        //Initialise la partition
        par = this.arezzo.getPartition();

        //Initialise la liste d'instruments
        listInstruments = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
    }

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
    @FXML
    public void initialize(){
        //Définit la liste d'instruments dans la ChoiceBox prédéfinit et définit le Piano comme valeur par défaut
        boxInstruments.setItems(listInstruments);
        boxInstruments.setValue("Piano");

        //Définit les éléments de style des curseurs, de l'arrière-plan et de la ChoiceBox
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
        //Modifie l'octave des notes qui vont être jouées en fonction du bouton qui est sélectionné
        arezzo.setHauteur(((RadioButton) groupHauteurs.getSelectedToggle()).getId());

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Définit la durée de la note depuis les différents boutons de durée de notes.
     */
    @FXML
    public void setDuree(){
        //Modifie la durée des notes qui vont être jouées en fonction du bouton qui est sélectionné
        arezzo.setDuree(((RadioButton) groupDuree.getSelectedToggle()).getId());

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Définit le tempo de la partition depuis le curseur.
     */
    @FXML
    public void setTempoSlider(){
        //Modifie le tempo en fonction de la valeur indiquée sur le curseur
        arezzo.setTempo(tempo.getValue());

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Définit le volume du pc depuis le curseur (linux uniquement).
     */
    @FXML
    public void setVolume() {
        //Modifie le volume en fonction de la valeur indiquée sur le curseur
        par.setVolume(volume.getValue());

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Remet tous les boutons et curseurs à leur valeur par défaut.
     */
    public void reInitialize(){
        //Remet les boutons durée et octave sur croche et medium par défaut
        croche.setSelected(true);
        medium.setSelected(true);

        //Remet la ChoiceBox sur l'instrument Piano par défaut
        boxInstruments.setValue("Piano");

        //Remet le curseur volume et tempo à 80 et 180 par défaut
        volume.setValue(80);
        tempo.setValue(180);

        //Permet d'éviter que cette fonction soit appelé en boucle dû à la fonction réagir ci-dessous
        arezzo.setNouveauProjet(false);
    }

    @Override
    public void reagir() {
        //Permet d'actualiser l'instrument choisit actuellement
        par.setInstrument(boxInstruments.getValue());

        //Actualise la valeur du tempo sur le curseur (quand on ouvre un fichier par exemple)
        tempo.setValue(arezzo.getTempo());

        //Si on fait un nouveau projet, on remet les paramètres par défaut
        if(arezzo.getNouveauProjet()){
            reInitialize();
        }
    }
}
