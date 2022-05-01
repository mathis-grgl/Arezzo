import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        Parent menu = FXMLLoader.load(getClass().getResource("fxml/menu.fxml"));
        root.setTop(menu);
        Parent piano = FXMLLoader.load(getClass().getResource("fxml/piano.fxml"));
        root.setBottom(piano);
        primaryStage.setTitle("Arezzo");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
