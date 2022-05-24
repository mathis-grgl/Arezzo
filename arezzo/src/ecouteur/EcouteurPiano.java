package ecouteur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import model.Arezzo;
import partition.Partition;

public class EcouteurPiano implements Observateur {
    private Arezzo arezzo;
    private Partition par;

    public EcouteurPiano(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();
    }

    @FXML
    public void jouerNotes(MouseEvent event){
        String note = event.getPickResult().getIntersectedNode().getId();
        String noteABC = arezzo.conversionNotesVersABC(note);
        String finalNote = arezzo.getNotationHauteurDuree(noteABC);
        par.play(finalNote);
        arezzo.notifierObservateur();
    }

    @FXML
    public void jouerSilence(){
        arezzo.getNotationHauteurDuree("z");
        arezzo.notifierObservateur();
    }

    @FXML
    public void initialize(){
    }

    @Override
    public void reagir() {}
}