package controller.layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ProjectDetailController {
    @FXML
    private Button menuDashboard, menuIssue, menuMember, menuSetting, menuPersonal;

    @FXML
    private BorderPane contentLayout;

    @FXML
    private void onChangeScene(MouseEvent event) {
        Button src = (Button) event.getSource();
        String id = src.getId();

        switch (id) {
            case "menuDashboard" -> {
                System.out.println("menuDashboard");
            }
            case "menuIssue" -> {
                System.out.println("menuIssue");
            }
            case "menuMember" -> {
                System.out.println("menuMember");
            }
            case "menuSetting" -> {
                System.out.println("menuSetting");
            }
            case "menuPersonal" -> {
                System.out.println("menuPersonal");
            }
            default -> {

            }
        }
    }
}
