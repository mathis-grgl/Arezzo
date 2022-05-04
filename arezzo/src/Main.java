import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import partition.Partition;
import vues.VueInstruments;
import vues.VueMenu;
import vues.VuePiano;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        Partition partition = new Partition(synthesizer);

        FXMLLoader menu = new FXMLLoader();
        menu.setLocation(getClass().getResource("fxml/menu.fxml"));
        menu.setControllerFactory(iC-> new VueMenu(partition));

        FXMLLoader piano = new FXMLLoader();
        piano.setLocation(getClass().getResource("fxml/piano.fxml"));
        piano.setControllerFactory(iC->new VuePiano(partition));

        FXMLLoader instruments = new FXMLLoader();
        instruments.setLocation(getClass().getResource("fxml/instruments.fxml"));
        instruments.setControllerFactory(iC->new VueInstruments(partition));


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
