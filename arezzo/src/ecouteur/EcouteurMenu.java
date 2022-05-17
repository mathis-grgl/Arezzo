package ecouteur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import model.Arezzo;
import partition.Partition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        titre.setText(arezzo.getTitre());
        arezzo.notifierObservateur();

    }

    @FXML
    public void ouvrirFichier(){
        FileChooser fileChooser = new FileChooser();

        Popup pop = new Popup();
        File file = fileChooser.showOpenDialog(pop);

        try {
            String contenu = Files.readString(file.toPath());
            Pattern pattern = Pattern.compile(":\".+\",",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(contenu);
            if(matcher.find()) {
                String titreADonner = matcher.group().substring(2, matcher.group().length() - 2);
                arezzo.setTitre(titreADonner);
                titre.setText(titreADonner);
            }
            pattern = Pattern.compile("melodie\":\".+\"\\s}",Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(contenu);
            if(matcher.find()) {
                String melodie = matcher.group().substring(10, matcher.group().length() - 3);
                arezzo.setMelodie(melodie);
            }
            arezzo.notifierObservateur();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enregistrerSous() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(arezzo.getTitre()+".json");

        Popup pop= new Popup();
        File file = fileChooser.showSaveDialog(pop);

        arezzo.setTitre(file.getName().replaceAll("[.]json",""));
        titre.setText(arezzo.getTitre());

        String contenu = "{ \"titre\":\"" + arezzo.getTitre() + "\",\"melodie\":\"" + arezzo.getMelodie() + "\" }";

        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(contenu);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        dialogue.setContentText("Nouveau nom pour " + arezzo.getTitre());

        Optional<String> out = dialogue.showAndWait();
        out.ifPresent(nom -> {
            arezzo.setTitre(nom);
            titre.setText(arezzo.getTitre());
        });
        arezzo.notifierObservateur();
    }

    @FXML
    public void transposerNotes(){}

    @Override
    public void reagir() {
    }
}




