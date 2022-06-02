package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Arezzo;

import java.io.IOException;
import java.util.Optional;


/**
 * L'écouteur lié à la VuePlayEtNotes.
 */
public class EcouteurPlayEtNotes implements Observateur {
    @FXML
    private AnchorPane fenetrePlay;
    @FXML
    private ImageView affichage,stop,play,delete;
    private Arezzo arezzo;
    private Stage list;

    /**
     * Instancie une nouvelle instance de EcouteurPlayEtNotes.
     * @param arezzo Le model Arezzo
     */
    public EcouteurPlayEtNotes(Arezzo arezzo) {
        //Initialise le model et ajoute l'écouteur en tant qu'Observateur
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        //Initialise la fenêtre de composition
        list = new Stage();
    }

    @FXML
    public void initialize(){
        fenetrePlay.setStyle("-fx-background-color: #B6E2D3");
    }

    /**
     * Joue la mélodie en entier.
     */
    @FXML
    public void playMelodie(){
        //Notifie les observateurs
        arezzo.notifierObservateurs();

        //Joue la mélodie dans le model
        arezzo.jouerMelodie();
    }

    /**
     * Supprime toute la mélodie.
     */
    @FXML
    public void deleteMelodie(){
        //Supprime toute la mélodie dans le model
        arezzo.deleteMelodie();

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Arrête de jouer la mélodie.
     */
    public void stopMelodie(){
        //Joue une mélodie vide pour arrêter la précédente
        arezzo.playMelodieVide();

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Ouvre une fenêtre comportant la liste de la composition de la mélodie avec des actions possibles sur les notes.
     */
    @FXML
    public void affichageNotes() throws IOException {
        //Déclaration et initialisation de la composition
        FXMLLoader composition = new FXMLLoader();
        composition.setLocation(getClass().getResource("/VueComposition.fxml"));
        composition.setControllerFactory(iC->new EcouteurComposition(arezzo));

        //Déclare et initialise la fenêtre comportant la composition de la liste des notes
        list.setTitle(arezzo.getTitre());
        list.setScene(new Scene(composition.load(), 300, 500));

        //Notifie les observateurs
        arezzo.notifierObservateurs();

        //Affiche la fenêtre comportant la composition de la liste des notes
        list.show();
    }

    @Override
    public void reagir() {
        if(!list.isFocused()) list.close();
    }
}
