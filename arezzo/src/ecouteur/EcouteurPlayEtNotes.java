package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Arezzo;

import java.util.List;
import java.util.Optional;


public class EcouteurPlayEtNotes implements Observateur {
    @FXML
    private AnchorPane fenetrePlay;
    @FXML
    private ImageView affichage,stop,play,delete;
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

    @FXML
    public void initialize(){
        fenetrePlay.setStyle("-fx-background-color: #B6E2D3;");
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
                    if (!note.equals("|")) {
                        setDisable(false);
                        String noteToClassique = arezzo.noteSansSurPlusMajuscule(note);
                        noteToClassique = arezzo.conversionNotesVersClassique(noteToClassique);
                        String duree;

                        String octave = arezzo.getOctaveNote(note);
                        octave = octave.toUpperCase();
                        octave = "  - {" + octave + "}";

                        if (arezzo.noteEstUnSilence(note)) {
                            duree = arezzo.getDureeSilence(note);
                            setText(noteToClassique);
                        } else {
                            duree = arezzo.getDureeNote(note);
                            setText(noteToClassique + octave);
                        }

                        ImageView imageNote = new ImageView();
                        imageNote.setImage(new Image("images/" + duree + ".png"));
                        imageNote.setPreserveRatio(true);
                        imageNote.setFitWidth(40);
                        imageNote.setFitHeight(40);

                        setGraphic(imageNote);

                    } else {
                        setDisable(true);
                        setText("Barre de mesure");
                        setGraphic(null);
                    }

                } else {
                    setText("");
                    setGraphic(null);
                }
            }
        });
    }

    private void initialisationMenuItem(){
        MenuItem supprimerSelection = new MenuItem("Supprimer la sélection");
        MenuItem augmenterSelection = new MenuItem("Augmenter d'un demi-ton");
        MenuItem descendreSelection = new MenuItem("Descendre d'un demi-ton");
        MenuItem couleurSelection = new MenuItem("Changer la couleur");

        supprimerSelection.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        descendreSelection.setAccelerator(new KeyCodeCombination(KeyCode.SUBTRACT));
        augmenterSelection.setAccelerator(new KeyCodeCombination(KeyCode.ADD));

        supprimerSelection.setOnAction(e-> supprimerListNotes());
        augmenterSelection.setOnAction(e-> transposerListNotes(1));
        descendreSelection.setOnAction(e-> transposerListNotes(-1));
        couleurSelection.setOnAction(e-> changerCouleurListNotes());

        cliqueDroitMenu.getItems().addAll(supprimerSelection,augmenterSelection,descendreSelection,couleurSelection);
    }

    private void changerCouleurListNotes() {
        if(!arezzo.estVide()) {
            TextInputDialog dialogue = new TextInputDialog();
            dialogue.setTitle("Modifier la couleur");
            dialogue.setHeaderText(null);
            dialogue.setContentText("Nouvelle couleur (en anglais) pour les notes sélectionnées : ");

            Optional<String> out = dialogue.showAndWait();
            out.ifPresent(couleur -> {
                for (int index : listNotes.getSelectionModel().getSelectedIndices())
                    arezzo.changerCouleurNote(index, couleur);
            });
            arezzo.notifierObservateur();
        }
    }

    private void transposerListNotes(int transposition) {
        if(!arezzo.estVide()) {
            arezzo.transposerNoteComposition(transposition, listNotes.getSelectionModel().getSelectedIndices());
            for (int index : listNotes.getSelectionModel().getSelectedIndices())
                listNotes.getItems().set(index, arezzo.getNoteMelodie(index));
        }
    }

    private void supprimerListNotes() {
        if(!arezzo.estVide()) {
            for (int index : listNotes.getSelectionModel().getSelectedIndices()) {
                if (!arezzo.nePeutPlusEtreSupprimer())
                    listNotes.getItems().remove(index);
            }
            arezzo.supprimerNote(listNotes.getSelectionModel().getSelectedIndices());
        }
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
        arezzo.notifierObservateur();
    }

    @FXML
    public void affichageNotes(){
        arezzo.notifierObservateur();
        listNotes.getItems().clear();
        ObservableList<String> liste = FXCollections.observableArrayList(arezzo.getListMelodie());
        listNotes.getItems().setAll(liste);
        list.show();
    }

    @Override
    public void reagir() {
        if(!list.isFocused()) list.close();
    }
}
