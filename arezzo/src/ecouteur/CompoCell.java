package ecouteur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Arezzo;

import java.io.IOException;

public class CompoCell extends ListCell<String> {
    private Arezzo arezzo;
    @FXML
    private ImageView dureeImageCell;
    @FXML
    private Label noteCell,octaveCell;
    @FXML
    private HBox hBoxCell;

    public CompoCell(Arezzo arezzo){
        this.arezzo = arezzo;

        FXMLLoader cell = new FXMLLoader();
        cell.setLocation(getClass().getResource("/CompoCell.fxml"));
        cell.setControllerFactory(iC-> this);

        try {
            cell.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(String note, boolean estVide) {
        super.updateItem(note, estVide);
        if (estVide){

            //Si la cellule ne contient rien on affiche rien
            noteCell.setText("");
            octaveCell.setText("");
            dureeImageCell.setImage(null);

        } else {

            //Si c'est une vraie note
            if (!note.equals("|")) {

                //On la rend sélectionnable
                setDisable(false);

                //Crée un string de la note sans octave ni durée
                String noteToClassique = arezzo.noteSansSurPlusMajuscule(note);

                //Note ABC -> Note classique
                noteToClassique = arezzo.conversionNotesVersClassique(noteToClassique);

                //Définit le texte avec la note seulement
                noteCell.setText(noteToClassique);

                //Initialise une variable contenant l'octave de la note (aigu,medium,grave)
                if(!arezzo.noteEstUnSilence(note))
                    octaveCell.setText(arezzo.getOctaveNote(note));

                //Iinitialisation de l'image durée avec l'url de l'image
                dureeImageCell.setImage(new Image(getClass().getResourceAsStream("/images/" + arezzo.getDureeNote(note) + ".png")));

            } else {

                //Si c'est une barre de mesure on désactive la sélection
                setDisable(true);

                //On définit le texte de la cellule avec "Barre de mesure"
                noteCell.setText("|");

                octaveCell.setText("");

                //On définit pas d'image pour une barre de mesure
                dureeImageCell.setImage(null);
            }
            setGraphic(hBoxCell);
        }
    }
}
