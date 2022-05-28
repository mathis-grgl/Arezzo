package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Arezzo;

/**
 * L'écouteur lié à la VueAffichageNotes.
 */
public class EcouteurAffichageNotes implements Observateur {
    private Arezzo arezzo;
    @FXML
    private ScrollPane affichage;
    @FXML
    private AnchorPane fenetreAffichage;

    /**
     * Instancie une nouvelle instance de EcouteurAffichageNotes.
     * @param arezzo Le model Arezzo
     */
    public EcouteurAffichageNotes(Arezzo arezzo){
        //Initialise arezzo et ajoute l'écouteur en tant qu'Observateur
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
    @FXML
    public void initialize(){
        //Modifie les couleurs de l'arrière-plan et de la fenêtre d'affichage des notes
        fenetreAffichage.setStyle("-fx-background-color: #B6E2D3;");
        affichage.setStyle("-fx-background: WHITE;");
    }

    @Override
    public void reagir() {
        //Rafraîchit l'image de la partition dans la fenêtre d'affichage des notes
        affichage.setContent(new ImageView(arezzo.getImage()));
    }
}
