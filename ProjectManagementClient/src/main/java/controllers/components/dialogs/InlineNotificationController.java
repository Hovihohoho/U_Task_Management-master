package controllers.components.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class InlineNotificationController {
    public static final String WARNING = "warning";
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String INFO = "info";

    @FXML
    private BorderPane pnNotify;

    @FXML
    private Label lbSubject;

    @FXML
    private Label lbContent;

    /**
     * @param subject đại diện cho tiêu đề của thông báo, thường là một đoạn văn ngắn gọn mô tả mục đích hoặc
     *                loại thông báo. Tiêu đề giúp người dùng nhanh chóng nhận diện được thông báo và
     *                hiểu rõ hơn về nội dung hoặc vấn đề mà thông báo đang truyền tải.
     * @param content đại diện cho nội dung chi tiết của thông báo, thường mô tả các bước cần thực hiện hoặc
     *                cung cấp thêm thông tin về sự kiện hoặc lỗi mà người dùng cần lưu ý.
     *                Đây là phần chi tiết hơn của thông báo và có thể chứa mô tả dài hơn so với subject.
     */
    public void setNotiData(String subject, String content) {
        pnNotify.getStyleClass().clear();
        pnNotify.getStyleClass().add(subject);

        lbSubject.setText(subject);
        lbContent.setText(content);
    }
}
