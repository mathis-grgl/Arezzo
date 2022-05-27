import ecouteur.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Arezzo;

public class Main extends Application {
    private HBox pianoEtBoutons;
    private FXMLLoader menu,affichageNotes;

    public void load(Arezzo arezzo) throws Exception{
        pianoEtBoutons = new HBox();

        //Déclaration et initialisation du menu et titre
        menu = new FXMLLoader();
        menu.setLocation(getClass().getResource("/VueMenu.fxml"));
        menu.setControllerFactory(iC-> new EcouteurMenu(arezzo));

        //Déclaration et initialisation du piano
        FXMLLoader piano = new FXMLLoader();
        piano.setLocation(getClass().getResource("/VuePiano.fxml"));
        piano.setControllerFactory(iC->new EcouteurPiano(arezzo));

        //Déclaration et initialisation des boutons instruments
        FXMLLoader instruments = new FXMLLoader();
        instruments.setLocation(getClass().getResource("/VueInstruments.fxml"));
        instruments.setControllerFactory(iC->new EcouteurInstruments(arezzo));

        //Déclaration et initialisation des boutons play et affichage notes
        FXMLLoader playEtNotes = new FXMLLoader();
        playEtNotes.setLocation(getClass().getResource("/VuePlayEtNotes.fxml"));
        playEtNotes.setControllerFactory(iC-> new EcouteurPlayEtNotes(arezzo));

        //Déclaration et initialisation de l'affichage des notes
        affichageNotes = new FXMLLoader();
        affichageNotes.setLocation(getClass().getResource("/VueAffichageNotes.fxml"));
        affichageNotes.setControllerFactory(iC-> new EcouteurAffichageNotes(arezzo));

        //Organisation du piano et de ses boutons
        pianoEtBoutons.getChildren().add(piano.load());
        pianoEtBoutons.getChildren().add(instruments.load());
        pianoEtBoutons.getChildren().add(playEtNotes.load());
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Initialisation Arezzo et root
        Arezzo arezzo = new Arezzo();
        BorderPane root = new BorderPane();

        //Chargement des FXMLLoader (vues) et leurs controllers (écouteurs)
        load(arezzo);

        //Ajout des éléments à l'interface
        root.setTop(menu.load());
        root.setBottom(pianoEtBoutons);
        root.setCenter(affichageNotes.load());

        //Eléments principaux de la fenêtre
        primaryStage.setResizable(false);
        primaryStage.setTitle("Arezzo");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
