package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Arezzo;



public class EcouteurAffichageNotes implements Observateur {
    private Arezzo arezzo;
    @FXML
    private ScrollPane affichage;
    @FXML
    private AnchorPane fenetreAffichage;

    public EcouteurAffichageNotes(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    @FXML
    public void initialize(){
        fenetreAffichage.setStyle("-fx-background-color: #B6E2D3;");
        affichage.setStyle("-fx-background: WHITE;");
    }

    @Override
    public void reagir() {
        affichage.setContent(new ImageView(arezzo.getImage()));
    }
}
