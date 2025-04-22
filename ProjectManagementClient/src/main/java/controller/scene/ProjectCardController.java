//package controller.component;
//
//import jakarta.persistence.EntityManager;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.BorderPane;
//import model.Project;
//import model.ProjectStatus;
//
//import java.io.IOException;
//import java.time.format.DateTimeFormatter;
//
//public class ProjectCardController {
//    @FXML
//    private BorderPane projectCard;
//
//    @FXML
//    private Label projectNameLabel;
//
//    @FXML
//    private Label statusLabel;
//
//    @FXML
//    private Label projectDescriptionLabel;
//
//    @FXML
//    private Label startDateLabel;
//
//    @FXML
//    private Label endDateLabel;
//
//    @FXML
//    private Label memberCountLabel;
//
//    @FXML
//    private Button viewProjectBtn;
//
//    @FXML
//    private Button editProjectBtn;
//
//    private Project project;
//    private EntityManager entityManager;
//    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//    public static BorderPane createProjectCard(Project project, EntityManager entityManager) throws IOException {
//        FXMLLoader loader = new FXMLLoader(ProjectCardController.class.getResource("/view/component/project_card.fxml"));
//        BorderPane card = loader.load();
//        ProjectCardController controller = loader.getController();
//        controller.setProject(project);
//        controller.setEntityManager(entityManager);
//        controller.setupEventHandlers();
//        return card;
//    }
//
//    public void initialize() {
//        // Được gọi tự động khi FXML được load
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//        updateProjectCard();
//    }
//
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    private void updateProjectCard() {
//        if (project == null) return;
//
//        projectNameLabel.setText(project.getName());
//        projectDescriptionLabel.setText(project.getDescription());
//
//        if (project.getStartDate() != null) {
//            startDateLabel.setText(project.getStartDate().format(dateFormatter));
//        } else {
//            startDateLabel.setText("Chưa xác định");
//        }
//
//        if (project.getEndDate() != null) {
//            endDateLabel.setText(project.getEndDate().format(dateFormatter));
//        } else {
//            endDateLabel.setText("Chưa xác định");
//        }
//
//        if (project.getAssigned() != null) {
//            memberCountLabel.setText(String.valueOf(project.getAssigned().size()));
//        } else {
//            memberCountLabel.setText("0");
//        }
//
//        // Cập nhật màu sắc của status label dựa vào trạng thái dự án
//        statusLabel.setText(project.getStatus().toString());
//        updateStatusStyle();
//    }
//
//    private void updateStatusStyle() {
//        statusLabel.getStyleClass().removeAll("status-active", "status-completed", "status-hold");
//
//        if (project.getStatus() == ProjectStatus.ACTIVE) {
//            statusLabel.getStyleClass().add("status-active");
//        } else if (project.getStatus() == ProjectStatus.COMPLETE) {
//            statusLabel.getStyleClass().add("status-completed");
//        } else if (project.getStatus() == ProjectStatus.ON_HOLD) {
//            statusLabel.getStyleClass().add("status-hold");
//        }
//    }
//
//    private void setupEventHandlers() {
//        viewProjectBtn.setOnAction(event -> {
//            // Mở màn hình chi tiết dự án
//            System.out.println("Xem dự án: " + project.getName());
//            // TODO: Implement view project details
//        });
//
//        editProjectBtn.setOnAction(event -> {
//            // Mở modal chỉnh sửa dự án
//            System.out.println("Chỉnh sửa dự án: " + project.getName());
//            try {
//                showEditProjectModal();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    private void showEditProjectModal() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/component/project_edit_modal.fxml"));
//        BorderPane modal = loader.load();
//        ProjectEditModalController controller = loader.getController();
//        controller.setProject(project);
//        controller.setEntityManager(entityManager);
//
//        // Hiển thị modal
//        // Phần này tùy thuộc vào cách bạn quản lý modal trong ứng dụng
//        // Có thể sử dụng Stage riêng hoặc StackPane overlay
//    }
//}