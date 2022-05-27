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
    @FXML
    private Label titre;
    private Arezzo arezzo;

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
        //Fenêtre permettant d'ouvrir un fichier json
        Popup popup = new Popup();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        File file = fileChooser.showOpenDialog(popup);

        try {
            //Lit le contenu depuis le fichier
            String contenu = Files.readString(file.toPath());

            //Convertir le contenu pour trouver le titre
            Pattern pattern = Pattern.compile("\"titre\":\".+\",\"m",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(contenu);

            //Si on a trouvé le titre on le met dans un String et on set le titre
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


            //Convertir le contenu pour trouver le tempo
            pattern = Pattern.compile("tempo\":\".+\"\\s}",Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(contenu);

            //Si on a trouvé le tempo on le met dans un double et on set le tempo
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

        //Fenêtre permettant d'enregistrer un fichier sous le format .json
        Popup pop = new Popup();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(arezzo.getTitre()+".json");
        File file = fileChooser.showSaveDialog(pop);

        //Remplace le titre de la mélodie sur l'affichage et dans le model, avec le nom du fichier qu'on vient d'enregistrer
        arezzo.setTitre(file.getName().replaceAll("[.]json",""));
        titre.setText(arezzo.getTitre());

        //Vérifie que le contenu de la mélodie en String correspond à l'ArrayList mélodie
        arezzo.convertirListEnMelodie();

        //Convertit le titre, la mélodie et le tempo en un string pour définir le contenu du fichier
        String contenu = "{ \"titre\":\"" + arezzo.getTitre() + "\",\"melodie\":\"" + arezzo.getMelodie() + "\",\"tempo\":\"" + arezzo.getTempo() + "\" }";

        //Enregistre le contenu dans le fichier
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




