package ecouteur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Arezzo;

import java.util.Optional;

public class EcouteurComposition implements Observateur{
    @FXML
    private ListView<String> listNotes;
    private Arezzo arezzo;

    public EcouteurComposition(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
    }

    @FXML
    public void initialize(){
        //Initialise la listView et ses paramètres
        listNotes.setFixedCellSize(40);
        listNotes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Définit l'affichage des cellules de la composition
        listNotes.setCellFactory(iC -> new CompoCell(arezzo));
    }

    /**
     * Change la couleur des notes sélectionnées en fonction de la chaîne de caractère fournit dans la fenêtre de dialogue.
     */
    @FXML
    public void changerCouleur() {
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
     * Transpose les notes sélectionnées de +1.
     */
    @FXML
    public void transposerPlusUn() {
        //S'il y a des notes dans la mélodie du model
        if(!arezzo.estVide()) {
            
            //Tranpose les notes sélectionnées dans la liste des notes du model en fonction de l'entier en paramètre
            arezzo.transposerNoteComposition(1, listNotes.getSelectionModel().getSelectedIndices());

            //Notifie les observateurs
            arezzo.notifierObservateurs();

            //Modifie la composition de la liste des notes en fonction du résultat de la transposition des notes
            for (int index : listNotes.getSelectionModel().getSelectedIndices())
                listNotes.getItems().set(index, arezzo.getNoteMelodie(index));
        }
    }

    /**
     * Transpose les notes sélectionnées de -1.
     */
    @FXML
    public void transposerMoinsUn() {
        //S'il y a des notes dans la mélodie du model
        if(!arezzo.estVide()) {

            //Tranpose les notes sélectionnées dans la liste des notes du model en fonction de l'entier en paramètre
            arezzo.transposerNoteComposition(-1, listNotes.getSelectionModel().getSelectedIndices());

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
    @FXML
    private void supprimerNotes(){
        //S'il y a des notes dans la mélodie du model
        if(!arezzo.estVide()) {

            //Supprime les notes sélectionnées dans la liste des notes du model
            arezzo.ListeNotesSelectionneesToSilence(listNotes.getSelectionModel().getSelectedIndices());

            //Modifie la composition de la liste des notes en fonction du résultat de la transposition des notes
            for (int index : listNotes.getSelectionModel().getSelectedIndices())
                listNotes.getItems().set(index, arezzo.getNoteMelodie(index));

            //Notifie les observateurs
            arezzo.notifierObservateurs();
        }
    }

    @Override
    public void reagir() {
        listNotes.getItems().clear();
        for(String note : arezzo) listNotes.getItems().add(note);
    }
}
