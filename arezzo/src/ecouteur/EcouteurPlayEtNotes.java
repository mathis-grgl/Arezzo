package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Arezzo;


public class EcouteurPlayEtNotes implements Observateur {
    private Arezzo arezzo;

    public EcouteurPlayEtNotes(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    @FXML
    public void playMelodie(){
        arezzo.notifierObservateur();
        arezzo.jouerMelodie();
    }

    @FXML
    public void deleteMelodie(){
        arezzo.deleteMelodie();
        arezzo.notifierObservateur();
    }

    public void stopMelodie(){
        arezzo.playMelodieVide();
    }

    @FXML
    public void affichageNotes(){
        Stage list = new Stage();
        list.setTitle(arezzo.getTitre());
        ListView<String> listNotes = new ListView<>();
        listNotes.getItems().addAll(arezzo.getListSansMesure());

        listNotes.setFixedCellSize(40);
        listNotes.setCellFactory(entree -> new ListCell<>() {
            @Override
            public void updateItem(String note, boolean estVide) {
                ImageView imageNote = new ImageView();
                super.updateItem(note, estVide);
                if (!estVide) {
                    if(note.matches(".{1,3}/"))
                        imageNote.setImage(new Image("images/croche.png"));
                    if (note.matches(".{1,3}1"))
                        imageNote.setImage(new Image("images/noire.png"));
                    if (note.matches(".{1,3}2"))
                        imageNote.setImage(new Image("images/blanche.png"));
                    if (note.matches(".{1,3}4"))
                        imageNote.setImage(new Image("images/ronde.png"));



                    String noteSP = arezzo.noteSansSurPlusMajuscule(note);
                    noteSP = arezzo.conversionNotesVersClassique(noteSP);
                    setText(noteSP);


                    imageNote.setPreserveRatio(true);
                    imageNote.setFitWidth(40);
                    imageNote.setFitHeight(40);
                    setGraphic(imageNote);
                }
            }
        });

        list.setScene(new Scene(listNotes, 300, 500));
        list.show();
    }

    @Override
    public void reagir() {

    }
}
