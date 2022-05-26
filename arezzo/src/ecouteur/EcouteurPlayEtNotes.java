package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Arezzo;

import java.util.List;


public class EcouteurPlayEtNotes implements Observateur {
    private Arezzo arezzo;
    private ListView<String> listNotes;
    private ContextMenu cliqueDroitMenu;
    private Stage list;

    public EcouteurPlayEtNotes(Arezzo arezzo) {
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        cliqueDroitMenu = new ContextMenu();

        initialisationListNotes();
        initialisationMenuItem();

        list = new Stage();
        list.setTitle(arezzo.getTitre());
        list.setScene(new Scene(listNotes, 300, 500));
    }

    private void initialisationListNotes(){
        listNotes = new ListView<>();
        listNotes.setFixedCellSize(40);
        listNotes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listNotes.setContextMenu(cliqueDroitMenu);

        listNotes.setCellFactory(entree -> new ListCell<>() {
            @Override
            public void updateItem(String note, boolean estVide) {
                super.updateItem(note, estVide);
                if (!estVide) {
                    String noteToClassique = arezzo.noteSansSurPlusMajuscule(note);
                    noteToClassique = arezzo.conversionNotesVersClassique(noteToClassique);
                    String duree;

                    if(arezzo.noteEstUnSilence(note)) {
                        duree = arezzo.getDureeSilence(note);
                    }
                    else {
                        duree = arezzo.getDureeNote(note);
                    }

                    String octave = arezzo.getOctaveNote(note.substring(0,note.length()-1));
                    octave = octave.toUpperCase();
                    octave = "  - {"+octave+"}";

                    ImageView imageNote = new ImageView();
                    imageNote.setImage(new Image("images/"+duree+".png"));
                    imageNote.setPreserveRatio(true);
                    imageNote.setFitWidth(40);
                    imageNote.setFitHeight(40);

                    setText(noteToClassique+octave);
                    setGraphic(imageNote);
                }
            }
        });
    }

    private void initialisationMenuItem(){
        MenuItem supprimerSelection = new MenuItem("Supprimer la sélection");
        MenuItem augmenterSelection = new MenuItem("Augmenter d'un demi-ton");
        MenuItem descendreSelection = new MenuItem("Descendre d'un demi-ton");

        supprimerSelection.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        descendreSelection.setAccelerator(new KeyCodeCombination(KeyCode.SUBTRACT));
        augmenterSelection.setAccelerator(new KeyCodeCombination(KeyCode.ADD));

        supprimerSelection.setOnAction(e-> arezzo.supprimerNote(listNotes.getSelectionModel().getSelectedIndices()));
        augmenterSelection.setOnAction(e-> arezzo.transposerNoteComposition(1,listNotes.getSelectionModel().getSelectedIndices()));
        descendreSelection.setOnAction(e-> arezzo.transposerNoteComposition(-1,listNotes.getSelectionModel().getSelectedIndices()));

        cliqueDroitMenu.getItems().addAll(supprimerSelection,augmenterSelection,descendreSelection);
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
        arezzo.notifierObservateur();
        ObservableList<String> liste = FXCollections.observableArrayList(arezzo.getListSansMesure());
        listNotes.getItems().clear();
        listNotes.getItems().setAll(liste);
        list.show();
    }

    @Override
    public void reagir() {
        if(!list.isFocused()) list.close();
    }
}
