package controller.scene;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import util.FXMLUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthLayoutController implements Initializable {

    @FXML
    private StackPane navAuthScreen;

    private BorderPane loginScreen, registerScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            FXMLUtil.Result resultLoginFXML = FXMLUtil.loadFXMLWithResult("/view/scene/login.fxml", LogInScreenController.class);
            loginScreen = (BorderPane) resultLoginFXML.parent();
            LogInScreenController loginController = (LogInScreenController) resultLoginFXML.controller();
            loginController.setAuthLayoutController(this);
            FXMLUtil.Result resultRegisterFXML = FXMLUtil.loadFXMLWithResult("/view/scene/register.fxml", RegisterScreenController.class);
            registerScreen = (BorderPane) resultRegisterFXML.parent();
            RegisterScreenController registerController = (RegisterScreenController) resultRegisterFXML.controller();
            registerController.setAuthLayoutController(this);

            registerScreen.setVisible(false);
            navAuthScreen.getChildren().addAll(loginScreen, registerScreen);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showRegisterScreen() {
        loginScreen.setVisible(false);
        registerScreen.setVisible(true);
    }

    public void showLoginScreen() {
        registerScreen.setVisible(false);
        loginScreen.setVisible(true);
    }
}
