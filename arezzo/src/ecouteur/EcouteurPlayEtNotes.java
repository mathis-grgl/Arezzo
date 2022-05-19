package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import model.Arezzo;


public class EcouteurPlayEtNotes implements Observateur {
    private Arezzo arezzo;

    public EcouteurPlayEtNotes(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    @FXML
    public void playMelodie(){
        arezzo.notifierObservateur();
        arezzo.jouerMelodie();
    }

    @FXML
    public void affichageNotes(){
        /*ListCell<String> template = new ListCell<>();
        ObservableList list = FXCollections.observableArrayList("Piano","Guitare","Saxophone","Trompette");
        ListView<String> listView = new ListView<>(list);
        listView.setCellFactory(v -> template);
        listView.setMaxHeight(200);
        Popup popup = new Popup();
        popup.getContent().add(listView);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        TextField input = new TextField();
        input.setBackground(null);
        popup.show(input,input.getLayoutX(),input.getLayoutY());*/
    }

    @Override
    public void reagir() {

    }
}
