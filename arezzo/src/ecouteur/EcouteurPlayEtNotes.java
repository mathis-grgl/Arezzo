package ecouteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Arezzo;
import java.util.Optional;


/**
 * L'écouteur lié à la VuePlayEtNotes.
 */
public class EcouteurPlayEtNotes implements Observateur {
    @FXML
    private AnchorPane fenetrePlay;
    @FXML
    private ImageView affichage,stop,play,delete;
    private Arezzo arezzo;
    private ListView<String> listNotes;
    private ContextMenu cliqueDroitMenu;
    private Stage list;

    /**
     * Instancie une nouvelle instance de EcouteurPlayEtNotes.
     * @param arezzo Le model Arezzo
     */
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

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
    @FXML
    public void initialize(){
        fenetrePlay.setStyle("-fx-background-color: #B6E2D3;");
    }

    /**
     * Initialise la liste de la composition de la mélodie (avec l'affichage de chaque note et leur texte).
     */
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

                        String octave = arezzo.getOctaveNote(note);
                        octave = octave.toUpperCase();
                        octave = "  - {" + octave + "}";

                        String duree;
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

    /**
     * Initialise les menus qui apparaissent après un clic droit sur une note.
     */
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

    /**
     * Change la couleur des notes sélectionnées en fonction de la chaîne de caractère fournit dans la fenêtre de dialogue.
     */
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

            arezzo.notifierObservateurs();
        }
    }

    /**
     * Transpose les notes sélectionnées en fonction du nombre de demi-tons rentré.
     * @param transposition Le nombre de demi-tons à transposer
     */
    private void transposerListNotes(int transposition) {
        if(!arezzo.estVide()) {

            arezzo.transposerNoteComposition(transposition, listNotes.getSelectionModel().getSelectedIndices());
            arezzo.notifierObservateurs();

            for (int index : listNotes.getSelectionModel().getSelectedIndices())
                listNotes.getItems().set(index, arezzo.getNoteMelodie(index));
        }
    }

    /**
     * Supprime les notes sélectionnées dans la mélodie.
     */
    private void supprimerListNotes() {
        if(!arezzo.estVide()) {

            for (int index : listNotes.getSelectionModel().getSelectedIndices()) {
                if (!arezzo.nePeutPlusEtreSupprimer())
                    listNotes.getItems().remove(index);
            }

            arezzo.supprimerNote(listNotes.getSelectionModel().getSelectedIndices());

            arezzo.notifierObservateurs();
        }
    }

    /**
     * Joue la mélodie en entier.
     */
    @FXML
    public void playMelodie(){
        arezzo.notifierObservateurs();
        arezzo.jouerMelodie();
    }

    /**
     * Supprime toute la mélodie.
     */
    @FXML
    public void deleteMelodie(){
        arezzo.deleteMelodie();
        arezzo.notifierObservateurs();
    }

    /**
     * Arrête de jouer la mélodie.
     */
    public void stopMelodie(){
        arezzo.playMelodieVide();
        arezzo.notifierObservateurs();
    }

    /**
     * Ouvre une fenêtre comportant la liste de la composition de la mélodie avec des actions possibles sur les notes.
     */
    @FXML
    public void affichageNotes(){
        arezzo.notifierObservateurs();

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
