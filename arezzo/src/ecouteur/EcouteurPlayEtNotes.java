package ecouteur;

import javafx.fxml.FXML;
import model.Arezzo;

import javax.swing.*;

public class EcouteurPlayEtNotes implements Observateur {
    private Arezzo arezzo;

    public EcouteurPlayEtNotes(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    @FXML
    public void playMelodie(){
        arezzo.jouerMelodie();
    }

    @FXML
    public void affichageNotes(){

    }

    @Override
    public void reagir() {

    }
}
