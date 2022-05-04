package vues;

import javafx.application.Platform;
import javafx.fxml.FXML;
import model.Arezzo;
import partition.Partition;


public class VueMenu implements Observateur{
    private Arezzo arezzo;
    private Partition par;

    public VueMenu(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
        par = this.arezzo.getPartition();
    }

    @FXML
    public void nouveauFichier(){
        arezzo.resetAll();
    }

    @FXML
    public void ouvrirFichier(){
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




