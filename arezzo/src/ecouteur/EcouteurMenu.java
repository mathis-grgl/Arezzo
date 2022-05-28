package ecouteur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import model.Arezzo;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * L'écouteur lié à la VueMenu.
 */
public class EcouteurMenu implements Observateur{
    @FXML
    private Label titre;
    @FXML
    private Pane fenetreMenu;
    @FXML
    private MenuBar affichageMenu;
    @FXML
    private Arezzo arezzo;

    /**
     * Instancie une nouvelle instance de EcouteurMenu.
     * @param arezzo Le model Arezzo
     */
    public EcouteurMenu(Arezzo arezzo){
        //Initialise le model et ajoute l'écouteur en tant qu'Observateur
        this.arezzo = arezzo;
        this.arezzo.ajouterObservateur(this);

        //Charge la police d'écriture BEECH
        Font.loadFont(String.valueOf(getClass().getResource("/font/BEECH.ttf")), -1);
    }

    /**
     * Initialise la partie graphique de la vue gérée dans l'écouteur.
     */
    @FXML
    public void initialize(){
        //Modifie la couleur du Menu et de l'arrière-plan en vert (avec une bordure vert-foncé pour le menu)
        fenetreMenu.setStyle("-fx-background-color: #B6E2D3;");
        affichageMenu.setStyle("-fx-background-color: #B6E2D3; -fx-border-color: #8ce2d3");

        //Modifie la police d'écriture et la couleur du titre
        titre.setStyle("-fx-font-family: BEECH;-fx-font-size: 30px;-fx-text-fill: #D8A7B1");
    }

    /**
     * Remet aux valeurs par défaut Arezzo, le titre et les modificateurs de notes (octaves, durée, instruments, tempo...).
     */
    @FXML
    public void nouveauFichier(){
        //Restaure par défaut les valeurs de du model, de l'affichage du titre et de tout le reste géré par les autres écouteurs
        arezzo.resetAll();
        titre.setText(arezzo.getTitre());

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Ouvre une fenêtre permettant d'ouvrir un fichier .json et convertit le fichier pour modifier le titre, la mélodie et le tempo.
     */
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

            //Si on a trouvé le titre on le met dans un String et on set le titre dans le model et sur l'affichage
            if(matcher.find()) {
                String titreADonner = matcher.group().substring(9, matcher.group().length() - 4);
                arezzo.setTitre(titreADonner);
                titre.setText(titreADonner);
            }


            //Convertir le contenu pour trouver la mélodie
            pattern = Pattern.compile("melodie\":\".+\",",Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(contenu);

            //Si on a trouvé la mélodie on la met dans un String et on set la mélodie et on convertit la mélodie en liste de notes dans le model
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

            //Notifie les observateurs
            arezzo.notifierObservateurs();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre une fenêtre permettant d'enregistrer un fichier .json et convertit le titre, la mélodie et le tempo en contenu pour le fichier.
     */
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

        //On convertit la liste des notes en la mélodie dans le model pour vérifier que le futur contenu soit correct.
        arezzo.convertirListEnMelodie();

        //Convertit le titre, la mélodie et le tempo en un string pour définir le contenu du fichier
        String contenu = "{ \"titre\":\"" + arezzo.getTitre() + "\",\"melodie\":\"" + arezzo.getMelodie() + "\",\"tempo\":\"" + arezzo.getTempo() + "\" }";

        //Enregistre le contenu dans le fichier
        try {
            //Déclaration et initialisation de l'écriveur du fichier
            PrintWriter writer;
            writer = new PrintWriter(file);

            //Permet d'écrire dans le fichier le contenu
            writer.println(contenu);

            //Ferme l'écriveur du fichier
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Ferme la fenêtre et la partition.
     */
    @FXML
    public void quitterFenetre(){
        //Ferme la fenêtre d'affichage
        Platform.exit();

        //Ferme la partition
        arezzo.fermerPartition();
    }

    /**
     * Permet de renommer la mélodie actuelle.
     */
    @FXML
    public void renommer() {
        //Initialise une fenêtre de dialogue
        TextInputDialog dialogue = new TextInputDialog();

        //Définit le titre de la fenêtre de dialogue
        dialogue.setTitle("Modifier le nom");

        //Définit le contenu de la fenêtre de dialogue
        dialogue.setHeaderText(null);
        dialogue.setContentText("Nouveau nom pour " + arezzo.getTitre());

        //Ouvre la fenêtre de dialogue et prend ce qui est entré pour modifier le titre
        Optional<String> out = dialogue.showAndWait();
        out.ifPresent(nom -> {

            //Modifie le titre dans le model puis dans l'affichage
            arezzo.setTitre(nom);
            titre.setText(arezzo.getTitre());
        });

        //Notifie les observateurs
        arezzo.notifierObservateurs();
    }

    /**
     * Permet de transposer toute la mélodie en fonction d'un entier à rentrer dans la fenêtre de dialogue qui s'ouvre.
     */
    @FXML
    public void transposerNotes(){
        //Initialise une fenêtre de dialogue
        TextInputDialog dialogue = new TextInputDialog();

        //Définit le titre de la fenêtre de dialogue
        dialogue.setTitle("Transposer les notes");

        //Définit le contenu de la fenêtre de dialogue
        dialogue.setHeaderText(null);
        dialogue.setContentText("De combien voulez vous transposer (en 1/2 tons) ?");

        //Ouvre la fenêtre de dialogue et prend ce qui est entré pour transposer les notes de la mélodie
        Optional<String> out = dialogue.showAndWait();
        out.ifPresent(entierString -> {

            //Convertit le String contenant l'entier en entier
            int entier = Integer.parseInt(entierString);

            //Vérifie que l'entier reste petit
            if(entier>=-100 && entier<=99) {

                //Transpose les notes de la mélodie en fonction de l'entier entré
                arezzo.transposerNotesArezzo(entier);

                //Notifie les observateurs
                arezzo.notifierObservateurs();

            } else {
                //Sinon prévient que l'entier rentré est incorrect
                System.err.println("L'entier rentré est incorrect");
            }});
    }

    @Override
    public void reagir() {
    }
}




