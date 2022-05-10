package ecouteur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import model.Arezzo;
import partition.Partition;

import java.util.Optional;


public class EcouteurMenu implements Observateur{
    private Arezzo arezzo;
    private Partition par;
    @FXML
    private Label titre;

    public EcouteurMenu(Arezzo arezzo){
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
    public void renommer() {
        TextInputDialog dialogue = new TextInputDialog();
        dialogue.setTitle("Modifier le nom");
        dialogue.setHeaderText(null);
        dialogue.setContentText("Nouveau nom pour " + arezzo.getPartition().getTitre());

        Optional<String> out = dialogue.showAndWait();
        out.ifPresent(nom -> {
            arezzo.getPartition().setTitre(nom);
        });
        arezzo.notifierObservateur();
    }

    @FXML
    public void transposerNotes(){}

    @Override
    public void reagir() {
        titre.setText(par.getTitre());
    }
}




