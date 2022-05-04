package vues;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import partition.Partition;

import javax.sound.midi.Synthesizer;


public class VueMenu {
    private Partition par;
    public Label titre;

    public VueMenu(Partition par){
        this.par = par;
    }

    @FXML
    public void nouveauFichier(){}

    @FXML
    public void ouvrirFichier(){
        //this.titre.setText("AHAHAHAHAH");
    }

    @FXML
    public void enregistrerSous(){}

    @FXML
    public void quitterFenetre(){
        Platform.exit();
        par.close();
    }

    @FXML
    public void renommerNotes(){}

    @FXML
    public void transposerNotes(){}
}




