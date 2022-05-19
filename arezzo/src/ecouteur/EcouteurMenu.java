package ecouteur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import model.Arezzo;
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
    @FXML
    private Label titre;

    public EcouteurMenu(Arezzo arezzo){
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);
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
        Popup popup = new Popup();
        File file = fileChooser.showOpenDialog(popup);

        try {
            String contenu = Files.readString(file.toPath());

            //Convertir le contenu pour trouver le titre
            Pattern pattern = Pattern.compile("\"titre\":\".+\",\"m",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(contenu);

            //Si on a trouvé le titre on la met dans un String et on set le titre
            if(matcher.find()) {
                String titreADonner = matcher.group().substring(9, matcher.group().length() - 4);
                arezzo.setTitre(titreADonner);
                titre.setText(titreADonner);
            }


            //Convertir le contenu pour trouver la mélodie
            pattern = Pattern.compile("melodie\":\".+\",",Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(contenu);

            //Si on a trouvé la mélodie on la met dans un String et on set la Mélodie
            if(matcher.find()) {
                String melodie = matcher.group().substring(10, matcher.group().length() - 2);
                arezzo.setMelodie(melodie);
                arezzo.convertirMelodieEnList();
            }


            //Convertir le contenu pour trouver la mélodie
            pattern = Pattern.compile("tempo\":\".+\"\\s}",Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(contenu);

            //Si on a trouvé la mélodie on la met dans un String et on set la Mélodie
            if(matcher.find()) {
                String tempo = matcher.group().substring(8, matcher.group().length() - 3);
                arezzo.setTempo(Double.valueOf(tempo));
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

        Popup pop = new Popup();
        File file = fileChooser.showSaveDialog(pop);

        arezzo.setTitre(file.getName().replaceAll("[.]json",""));
        titre.setText(arezzo.getTitre());

        arezzo.convertirListEnMelodie();
        String contenu = "{ \"titre\":\"" + arezzo.getTitre() + "\",\"melodie\":\"" + arezzo.getMelodie() + "\",\"tempo\":\"" + arezzo.getTempo() + "\" }";

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
        arezzo.fermerPartition();
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
    public void transposerNotes(){
        TextInputDialog dialogue = new TextInputDialog();
        dialogue.setTitle("Transposer les notes");
        dialogue.setHeaderText(null);
        dialogue.setContentText("De combien voulez vous transposer (en 1/2 tons) ?");

        Optional<String> out = dialogue.showAndWait();
        out.ifPresent(entierString -> {
            int entier = Integer.parseInt(entierString);
            if(entier>=0 && entier<=99) {
                arezzo.transposerNotesArezzo(entier);
            } else {
                System.err.println("L'entier rentré est incorrect");
            }});
    }

    @Override
    public void reagir() {
    }
}




