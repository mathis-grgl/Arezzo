package vues;

import javafx.application.Platform;
import javafx.fxml.FXML;


public class VueMenu {

    @FXML
    public void nouveauFichier(){}

    @FXML
    public void ouvrirFichier(){}

    @FXML
    public void enregistrerSous(){}

    @FXML
    public void quitterFenetre(){
        Platform.exit();
    }

    @FXML
    public void renommerNotes(){}

    @FXML
    public void transposerNotes(){}
}




