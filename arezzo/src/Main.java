import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import melodie.Arezzo;
import vues.VueInstruments;
import vues.VueMenu;
import vues.VuePiano;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Arezzo arezzo = new Arezzo();

        BorderPane root = new BorderPane();


        FXMLLoader menu = new FXMLLoader();
        menu.setLocation(getClass().getResource("fxml/menu.fxml"));
        menu.setControllerFactory(iC-> new VueMenu(arezzo));

        FXMLLoader piano = new FXMLLoader();
        piano.setLocation(getClass().getResource("fxml/piano.fxml"));
        piano.setControllerFactory(iC->new VuePiano(arezzo));

        FXMLLoader instruments = new FXMLLoader();
        instruments.setLocation(getClass().getResource("fxml/instruments.fxml"));
        instruments.setControllerFactory(iC->new VueInstruments(arezzo));


        root.setTop(menu.load());
        root.setBottom(piano.load());
        root.setRight(instruments.load());

        primaryStage.setTitle("Arezzo");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
