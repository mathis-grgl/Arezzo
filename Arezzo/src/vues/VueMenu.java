package vues;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import partition.Partition;


public class VueMenu {
    private Partition par;
    private Label titre;

    public VueMenu(/*Partition par,Label titre*/){
        /*this.par = par;
        this.titre = titre;*/
    }

    public void changementLabel(){
        titre.setText(par.getTitre());
    }

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




