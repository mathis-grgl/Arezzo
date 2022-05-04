import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Arezzo;
import vues.VueInstruments;
import vues.VueMenu;
import vues.VuePiano;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Arezzo arezzo = new Arezzo();

        BorderPane root = new BorderPane();
        HBox pianoEtBoutons = new HBox();

        //Déclaration et initialisation du menu et titre
        FXMLLoader menu = new FXMLLoader();
        menu.setLocation(getClass().getResource("fxml/menu.fxml"));
        menu.setControllerFactory(iC-> new VueMenu(arezzo));

        //Déclaration et initialisation du piano
        FXMLLoader piano = new FXMLLoader();
        piano.setLocation(getClass().getResource("fxml/piano.fxml"));
        piano.setControllerFactory(iC->new VuePiano(arezzo));

        //Déclaration et initialisation des boutons instruments
        FXMLLoader instruments = new FXMLLoader();
        instruments.setLocation(getClass().getResource("fxml/instruments.fxml"));
        instruments.setControllerFactory(iC->new VueInstruments(arezzo));

        //Organisation du piano et de ses boutons
        pianoEtBoutons.getChildren().add(piano.load());
        pianoEtBoutons.getChildren().add(instruments.load());


        //Ajout des éléments à l'interface
        root.setTop(menu.load());
        root.setBottom(pianoEtBoutons);

        //Eléments principaux de la fenêtre
        primaryStage.setTitle("Arezzo");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
