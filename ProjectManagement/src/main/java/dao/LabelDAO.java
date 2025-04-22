package dao;

import model.Label;
import model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class LabelDAO {
    private EntityManager em;
    public LabelDAO(EntityManager em) {
        this.em = em;
    }
    public Label getLabelById(Long id) {
        return em.find(Label.class, id);
    }
    public List<Label> getAllLabels() {
        String jpql = "SELECT l FROM Label l";
        return em.createQuery(jpql, Label.class).getResultList();
    }
    public Label getLabelByName(String name) {
        String jpql = "SELECT l FROM Label l WHERE l.name = :name";
        return em.createQuery(jpql, Label.class)
                .setParameter("name", name)
                .getSingleResult();
    }
    public boolean createLabel(String name, String description, String color, Long projectId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Project project = em.find(Project.class, projectId);
            if (project == null) {
                System.out.println("Không tìm thấy dự án.");
                return false;
            }
            Label label = new Label();
            label.setName(name);
            label.setDescription(description);
            label.setColor(color);
            label.setCreateAt(LocalDate.now());
            label.setUpdateAt(LocalDate.now());
            label.setProject(project);
            em.persist(label);
            tr.commit();
            System.out.println("Tạo Nhãn THành Công");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean updateLabel(Long labelId, String newName, String newDescription, String newColor) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Label label = em.find(Label.class, labelId);
            if (label == null) {
                System.out.println("Không tìm thấy NHãn.");
                return false;
            }
            label.setName(newName);
            label.setDescription(newDescription);
            label.setColor(newColor);
            label.setUpdateAt(LocalDate.now());
            em.merge(label);
            tr.commit();
            System.out.println("Nhãn đã được cập nhật.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean deleteLabel(Long labelId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Label label = em.find(Label.class, labelId);
            if (label == null) {
                System.out.println("Không tìm thấy nhãn.");
                return false;
            }
            em.remove(label);
            tr.commit();
            System.out.println("Nhãn đã được xóa.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
}
