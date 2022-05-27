package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.Arezzo;
import partition.Partition;

public class EcouteurPiano implements Observateur {
    @FXML
    private Rectangle Do,DoDiese,Re,ReDiese,Mi,Fa,FaDiese,Sol,SolDiese,La,LaDiese,Si;
    @FXML
    private Button chut;
    private Arezzo arezzo;
    private Partition par;

    public EcouteurPiano(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();
    }

    @FXML
    public void initialize(){
        Do.setStyle("-fx-fill: #FAE8E0");
        DoDiese.setStyle("-fx-fill: #D8A7B1");
        Re.setStyle("-fx-fill: #FAE8E0");
        ReDiese.setStyle("-fx-fill: #D8A7B1");
        Mi.setStyle("-fx-fill: #FAE8E0");
        Fa.setStyle("-fx-fill: #FAE8E0");
        FaDiese.setStyle("-fx-fill: #D8A7B1");
        Sol.setStyle("-fx-fill: #FAE8E0");
        SolDiese.setStyle("-fx-fill: #D8A7B1");
        La.setStyle("-fx-fill: #FAE8E0");
        LaDiese.setStyle("-fx-fill: #D8A7B1");
        Si.setStyle("-fx-fill: #FAE8E0");
        chut.setStyle("-fx-background-color: #EF7C8E");
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
    @Override
    public void reagir() {}
}