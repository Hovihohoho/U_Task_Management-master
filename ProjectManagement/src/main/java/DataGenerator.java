
import model.Priority;
import model.ProjectStatus;
import model.Role;
import model.StaffStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.*;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static void generateData() {
        EntityManager em = Persistence.createEntityManagerFactory("maria_unit")
                .createEntityManager();
        EntityTransaction tr = em.getTransaction();

        for (int i = 0; i < 10; i++) {
            tr.begin();

            // Tạo dữ liệu giả cho Staff và lưu vào DB
            Staff staff = new Staff();
            staff.setName(faker.name().fullName());
            staff.setEmail(faker.internet().emailAddress());
            staff.setPassword(faker.internet().password());
            staff.setRole(Role.values()[faker.number().numberBetween(0, Role.values().length)]);
            staff.setProfilePicture(faker.avatar().image());
            staff.setJoinedDate(LocalDate.now().minusYears(faker.number().numberBetween(1, 10)));
            staff.setStatus(StaffStatus.values()[faker.number().numberBetween(0, StaffStatus.values().length)]);
            staff.setPhones(generatePhones());

            em.persist(staff); // Lưu Staff vào DB trước

            // Tạo dữ liệu giả cho Project và gắn Staff vào
            Project project = new Project();
            project.setName(faker.company().name());
            project.setDescription(faker.lorem().sentence());
            project.setStartDate(LocalDate.now().minusMonths(faker.number().numberBetween(1, 12)));
            project.setEndDate(LocalDate.now().plusMonths(faker.number().numberBetween(1, 12)));
            project.setCreateAt(LocalDate.now());
            project.setUpdateAt(LocalDate.now());
            project.setStatus(ProjectStatus.values()[faker.number().numberBetween(0, ProjectStatus.values().length)]);
            project.setAssigned((Set<Staff>) staff);  // Gắn Staff vào Project

            em.persist(project); // Lưu Project vào DB

            // Tạo dữ liệu giả cho Issue
            Issue issue = new Issue();
            issue.setTitle(faker.lorem().sentence());
            issue.setDescription(faker.lorem().paragraph());
            issue.setPriority(Priority.values()[faker.number().numberBetween(0, Priority.values().length)]);
            issue.setCreateAt(LocalDate.now());
            issue.setUpdateAt(LocalDate.now());
            issue.setAssignee(staff); // Gắn Staff vào Issue
            issue.setProject(project);
            issue.setComments(generateComments(issue, staff)); // Đảm bảo rằng Staff đã được lưu khi tạo Comment

            em.persist(issue); // Lưu Issue vào DB

            // Tạo dữ liệu giả cho Label
            Label label = new Label();
            label.setName(faker.color().name());
            label.setDescription(faker.lorem().sentence());
            label.setColor(faker.color().hex());
            label.setCreateAt(LocalDate.now());
            label.setUpdateAt(LocalDate.now());
            label.setProject(project);

            em.persist(label); // Lưu Label vào DB

            // Tạo dữ liệu giả cho Attachment
            Attachment attachment = new Attachment();
            attachment.setName(faker.file().fileName());
            attachment.setPath(faker.file().fileName());
            attachment.setSize(faker.number().randomDouble(2, 1, 100));
            attachment.setCreateAt(LocalDate.now());
            attachment.setUpdateAt(LocalDate.now());
            attachment.setIssue(issue);

            em.persist(attachment); // Lưu Attachment vào DB

            // Tạo dữ liệu giả cho Comment và lưu vào DB
            Set<Comment> comments = generateComments(issue, staff);
            comments.forEach(em::persist);

            tr.commit();
        }
        em.close();
    }

    // Cập nhật phương thức generateComments để nhận vào staff đã lưu
    private static Set<Comment> generateComments(Issue issue, Staff staff) {
        Set<Comment> comments = new HashSet<>();
        for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
            Comment comment = new Comment();
            comment.setContent(faker.lorem().sentence());
            comment.setCreateAt(LocalDate.now());
            comment.setUpdateAt(LocalDate.now());
            comment.setIssue(issue);
            comment.setStaff(staff); // Gắn Staff đã lưu vào Comment
            comments.add(comment);
        }
        return comments;
    }


    // Phương thức để tạo danh sách số điện thoại giả
    private static Set<String> generatePhones() {
        Set<String> phones = new HashSet<>();
        phones.add(faker.phoneNumber().cellPhone());
        phones.add(faker.phoneNumber().cellPhone());
        return phones;
    }

    // Phương thức để tạo Staff giả
    private static Staff generateStaff() {
        Staff staff = new Staff();
        staff.setName(faker.name().fullName());
        staff.setEmail(faker.internet().emailAddress());
        staff.setPassword(faker.internet().password());
        staff.setRole(Role.values()[faker.number().numberBetween(0, Role.values().length)]);
        staff.setProfilePicture(faker.avatar().image());
        staff.setJoinedDate(LocalDate.now().minusYears(faker.number().numberBetween(1, 10)));
        staff.setStatus(StaffStatus.values()[faker.number().numberBetween(0, StaffStatus.values().length)]);
        staff.setPhones(generatePhones());

        return staff;
    }
}
