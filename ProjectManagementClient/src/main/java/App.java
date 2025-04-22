import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.FXMLUtil;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            AnchorPane projectDetail = (AnchorPane) FXMLUtil.loadFXML("/view/layout/auth_layout.fxml");
            System.out.println(projectDetail);
            Scene scene = new Scene(projectDetail);
            stage.setScene(scene);
            stage.setTitle("Project Management");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting App...");
        launch(args);
    }
}
