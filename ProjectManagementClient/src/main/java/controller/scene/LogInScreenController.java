package controller.scene;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class LogInScreenController {
    @Setter
    private AuthLayoutController authLayoutController;

    @FXML
    private VBox loginScreen;

    @FXML
    private TextField txtEmail;

    @FXML
    private void handleLogIn() {

    }

    @FXML
    private void onChangeToRegisterScreen() {
        authLayoutController.showRegisterScreen();
    }
}
