//package controller.scene;
//
//import controller.scene.ProjectCardController;
//import dao.ProjectDAO;
//import jakarta.persistence.EntityManager;
//import javafx.fxml.FXML;
//import javafx.scene.layout.FlowPane;
//import model.Project;
//
//import java.io.IOException;
//import java.util.List;
//
//public class ProjectManagementController {
//    @FXML
//    private FlowPane createdProjectsPane;
//
//    @FXML
//    private FlowPane participatedProjectsPane;
//
//    private EntityManager entityManager;
//    private Long currentUserId; // ID của người dùng hiện tại
//
//    public void initialize() {
//        // Được gọi tự động khi FXML được load
//    }
//
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    public void setCurrentUserId(Long currentUserId) {
//        this.currentUserId = currentUserId;
//    }
//
//    public void loadProjects() {
//        // Xóa tất cả project card hiện tại
//        createdProjectsPane.getChildren().clear();
//        participatedProjectsPane.getChildren().clear();
//
//        // Lấy danh sách dự án từ database
//        ProjectDAO projectDAO = new ProjectDAO(entityManager);
//        List<Project> allProjects = projectDAO.getAllProjects();
//
//        if (allProjects != null) {
//            for (Project project : allProjects) {
//                try {
//                    // Tạo project card và thêm vào flow pane tương ứng
//                    if (isProjectCreatedByCurrentUser(project)) {
//                        createdProjectsPane.getChildren().add(
//                                ProjectCardController.createProjectCard(project, entityManager)
//                        );
//                    } else if (isCurrentUserParticipatedInProject(project)) {
//                        participatedProjectsPane.getChildren().add(
//                                ProjectCardController.createProjectCard(project, entityManager)
//                        );
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    // Kiểm tra xem dự án có phải do người dùng hiện tại tạo không
//    // Đây chỉ là mẫu, bạn cần điều chỉnh logic theo cấu trúc dữ liệu thực tế
//    private boolean isProjectCreatedByCurrentUser(Project project) {
//        // TODO: Implement logic để kiểm tra dự án có phải do người dùng hiện tại tạo không
//        // Ví dụ: return project.getCreator().getId().equals(currentUserId);
//        return true; // Mẫu - giả sử tất cả dự án đều do người dùng hiện tại tạo
//    }
//
//    // Kiểm tra xem người dùng hiện tại có tham gia vào dự án không
//    private boolean isCurrentUserParticipatedInProject(Project project) {
//        if (project.getAssigned() == null) return false;
//
//        return project.getAssigned().stream()
//                .anyMatch(staff -> staff.getId().equals(currentUserId));
//    }
//
//    // Hiển thị modal tạo dự án mới
//    @FXML
//    private void showCreateProjectModal() {
//        // TODO: Implement hiển thị modal tạo dự án
//    }
//}