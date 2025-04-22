package controller.scene;

import javafx.fxml.FXML;
import lombok.Setter;

public class RegisterScreenController {
    @Setter
    private AuthLayoutController authLayoutController;

    @FXML
    private void handleRegister() {

    }

    @FXML
    private void onChangeToLoginScreen() {
        authLayoutController.showLoginScreen();
    }
}
