package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import model.Arezzo;

public class EcouteurAffichageNotes implements Observateur {
    private Arezzo arezzo;
    @FXML
    private ScrollPane affichage;

    public EcouteurAffichageNotes(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        affichage.setContent(new ImageView(arezzo.getImage()));
    }
}
