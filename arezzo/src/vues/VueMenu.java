package vues;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import melodie.Arezzo;
import partition.Partition;

import javax.sound.midi.Synthesizer;


public class VueMenu implements Observateur{
    private Arezzo arezzo;

    public VueMenu(Arezzo arezzo){
        this.arezzo = arezzo;
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

    @Override
    public void reagir() {

    }
}




