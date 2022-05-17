package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Arezzo;

public class EcouteurComposition implements Observateur{
    private Arezzo arezzo;
    @FXML
    private ListView<String> listComposition;
    @FXML
    private ObservableList<String> observableComposition;

    public EcouteurComposition(Arezzo arezzo){
        this.arezzo = arezzo;
        observableComposition = FXCollections.observableArrayList();
        this.arezzo.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        listComposition.getItems().clear();
        listComposition.getItems().addAll(observableComposition);
    }
}
