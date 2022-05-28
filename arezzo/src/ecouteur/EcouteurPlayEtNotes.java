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
        //Initialise le model et ajoute l'écouteur en tant qu'Observateur
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        //Initialise le ContextMenu qui s'affiche quand on clique droit sur une note
        cliqueDroitMenu = new ContextMenu();

        //Initialise la composition de la liste des notes
        initialisationListNotes();

        //Déclare et initialise les différents menus du ContextMenu
        initialisationMenuItem();

        //Déclare et initialise la fenêtre comportant la composition de la liste des notes
        list = new Stage();
        list.setTitle(arezzo.getTitre());
        list.setScene(new Scene(listNotes, 300, 500));
    }

    @FXML
    public void initialize(){
        fenetrePlay.setStyle("-fx-background-color: #B6E2D3");
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
                    //Si c'est une vraie note
                    if (!note.equals("|")) {
                        //On la rend sélectionnable
                        setDisable(false);

                        //Crée un string de la note sans octave ni durée
                        String noteToClassique = arezzo.noteSansSurPlusMajuscule(note);

                        //Note ABC -> Note classique
                        noteToClassique = arezzo.conversionNotesVersClassique(noteToClassique);

                        //Initialise une variable contenant l'octave de la note (aigu,medium,grave)
                        String octave = arezzo.getOctaveNote(note);

                        //Mise en page du String
                        octave = octave.toUpperCase();
                        octave = "  - {" + octave + "}";

                        String duree;
                        if (arezzo.noteEstUnSilence(note)) {
                            //Permet d'avoir la durée de la note (demiSoupir, etc)
                            duree = arezzo.getDureeSilence(note);

                            //Définit le texte avec la note seulement
                            setText(noteToClassique);
                        } else {
                            //Permet d'avoir la durée de la note (croche, etc)
                            duree = arezzo.getDureeNote(note);

                            //Définit le texte avec la note + l'octave
                            setText(noteToClassique + octave);
                        }

                        //Iinitialisation de l'image durée avec l'url de l'image
                        ImageView imageNote = new ImageView();
                        imageNote.setImage(new Image("images/" + duree + ".png"));

                        //Définit le ratio et la taille de l'image
                        imageNote.setPreserveRatio(true);
                        imageNote.setFitWidth(40);
                        imageNote.setFitHeight(40);

                        //On affiche l'image dans la composition de la liste des notes
                        setGraphic(imageNote);

                    } else {
                        //Si c'est une barre de mesure on désactive la sélection
                        setDisable(true);

                        //On définit le texte de la cellule avec "Barre de mesure"
                        setText("Barre de mesure");

                        //On définit pas d'image pour une barre de mesure
                        setGraphic(null);
                    }

                } else {
                    //Si la cellule ne contient rien on affiche rien
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
        //Initialise les menus du ContextMenu
        MenuItem supprimerSelection = new MenuItem("Supprimer la sélection");
        MenuItem augmenterSelection = new MenuItem("Augmenter d'un demi-ton");
        MenuItem descendreSelection = new MenuItem("Descendre d'un demi-ton");
        MenuItem couleurSelection = new MenuItem("Changer la couleur");

        //Associe une touche aux menus "Supprimer la sélection", "Augmenter d'un demi-ton" et "Descendre d'un demi-ton"
        supprimerSelection.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        descendreSelection.setAccelerator(new KeyCodeCombination(KeyCode.SUBTRACT));
        augmenterSelection.setAccelerator(new KeyCodeCombination(KeyCode.ADD));

        //Ajoute un écouteur à chacun des menus
        supprimerSelection.setOnAction(e-> supprimerListNotes());
        augmenterSelection.setOnAction(e-> transposerListNotes(1));
        descendreSelection.setOnAction(e-> transposerListNotes(-1));
        couleurSelection.setOnAction(e-> changerCouleurListNotes());

        //Ajoute tous les menus au ContextMenu
        cliqueDroitMenu.getItems().addAll(supprimerSelection,augmenterSelection,descendreSelection,couleurSelection);
    }

    /**
     * Change la couleur des notes sélectionnées en fonction de la chaîne de caractère fournit dans la fenêtre de dialogue.
     */
    private void changerCouleurListNotes() {
        //S'il y a des notes dans la mélodie du model
        if(!arezzo.estVide()) {

            //Initialise une fenêtre de dialogue
            TextInputDialog dialogue = new TextInputDialog();

            //Définit le titre de la fenêtre de dialogue
            dialogue.setTitle("Modifier la couleur");

            //Définit le contenu de la fenêtre de dialogue
            dialogue.setHeaderText(null);
            dialogue.setContentText("Nouvelle couleur (en anglais) pour les notes sélectionnées : ");

            //Ouvre la fenêtre de dialogue et prend ce qui est entré pour modifier la couleur des notes sélectionnées
            Optional<String> out = dialogue.showAndWait();
            out.ifPresent(couleur -> {

                //Pour toutes les notes sélectionnées on change leur couleur dans la liste des notes du model
                for (int index : listNotes.getSelectionModel().getSelectedIndices())
                    arezzo.changerCouleurNote(index, couleur);
            });

            //Notifie les observateurs
            arezzo.notifierObservateurs();
        }
    }

    /**
     * Transpose les notes sélectionnées en fonction du nombre de demi-tons rentré.
     * @param transposition Le nombre de demi-tons à transposer
     */
    private void transposerListNotes(int transposition) {
        //S'il y a des notes dans la mélodie du model
        if(!arezzo.estVide()) {

            //Tranpose les notes sélectionnées dans la liste des notes du model en fonction de l'entier en paramètre
            arezzo.transposerNoteComposition(transposition, listNotes.getSelectionModel().getSelectedIndices());

            //Notifie les observateurs
            arezzo.notifierObservateurs();

            //Modifie la composition de la liste des notes en fonction du résultat de la transposition des notes
            for (int index : listNotes.getSelectionModel().getSelectedIndices())
                listNotes.getItems().set(index, arezzo.getNoteMelodie(index));
        }
    }

    /**
     * Supprime les notes sélectionnées dans la mélodie.
     */
    private void supprimerListNotes() {
        //S'il y a des notes dans la mélodie du model
        if(!arezzo.estVide()) {

            //Supprime les notes sélectionnées dans la composition de la liste des notes
            for (int index : listNotes.getSelectionModel().getSelectedIndices()) {
                if (!arezzo.nePeutPlusEtreSupprimer())
                    listNotes.getItems().remove(index);
            }

            //Supprime les notes sélectionnées dans la liste des notes du model
            arezzo.supprimerNote(listNotes.getSelectionModel().getSelectedIndices());


            //Notifie les observateurs
            arezzo.notifierObservateurs();
        }
    }

    /**
     * Joue la mélodie en entier.
     */
    @FXML
    public void playMelodie(){
        //Notifie les observateurs
        arezzo.notifierObservateurs();

        //Joue la mélodie dans le model
        arezzo.jouerMelodie();
    }

    /**
     * Supprime toute la mélodie.
     */
    @FXML
    public void deleteMelodie(){
        //Supprime toute la mélodie dans le model
        arezzo.deleteMelodie();

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Arrête de jouer la mélodie.
     */
    public void stopMelodie(){
        //Joue une mélodie vide pour arrêter la précédente
        arezzo.playMelodieVide();

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Ouvre une fenêtre comportant la liste de la composition de la mélodie avec des actions possibles sur les notes.
     */
    @FXML
    public void affichageNotes(){
        //Notifie les observateurs
        arezzo.notifierObservateurs();

        //Efface la composition de la liste des notes
        listNotes.getItems().clear();

        //Déclare et initialise la liste des notes
        ObservableList<String> liste = FXCollections.observableArrayList(arezzo.getListMelodie());

        //Ajoute la liste des notes à la composition de la liste des notes
        listNotes.getItems().setAll(liste);

        //Affiche la fenêtre comportant la composition de la liste des notes
        list.show();
    }

    @Override
    public void reagir() {
        //Si on décide de faire une action sur la fenêtre principale, cela ferme la fenêtre comportant la composition de la liste des notes
        if(!list.isFocused()) list.close();
    }
}
