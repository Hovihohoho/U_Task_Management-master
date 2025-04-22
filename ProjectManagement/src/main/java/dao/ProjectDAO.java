package dao;

import model.Project;
import model.Staff;
import model.ProjectStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class ProjectDAO {
    private EntityManager em;
    public ProjectDAO(EntityManager em) {
        this.em = em;
    }
    public List<Project> getAllProjects() {
        try {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p", Project.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Project> getStaffByName(String name) {
        try {
            TypedQuery<Project> query = em.createQuery("SELECT s FROM Staff s WHERE s.name = :name", Project.class);
            query.setParameter("name", name);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Project getStaffById(Long projectId) {
        try {
            return em.find(Project.class, projectId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean createProject(Project project) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(project);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean addStaffToProject(Long projectId, List<Long> staffIds) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Project project = em.find(Project.class, projectId);
            if (project == null) {
                System.out.println("Không tìm thấy dự án.");
                return false;
            }
            List<Staff> staffList = em.createQuery("SELECT s FROM Staff s WHERE s.id IN :staffIds", Staff.class)
                    .setParameter("staffIds", staffIds)
                    .getResultList();
            if (staffList.isEmpty()) {
                System.out.println("Không tìm thấy nhân viên.");
                return false;
            }
            project.getAssigned().addAll(staffList);
            for (Staff staff : staffList) {
                staff.getProjects().add(project);
            }
            em.merge(project);
            for (Staff staff : staffList) {
                em.merge(staff);
            }
            tr.commit();
            System.out.println("Nhân viên đã được thêm vào dự án.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean removeStaffFromProject(Long staffId, Long projectId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Staff staff = em.find(Staff.class, staffId);
            if (staff == null) {
                System.out.println("Không tìm thấy nhân viên.");
                return false;
            }
            Project project = em.find(Project.class, projectId);
            if (project == null) {
                System.out.println("Không tìm thấy dự án.");
                return false;
            }
            project.getAssigned().remove(staff);
            em.merge(project);
            staff.getProjects().remove(project);
            em.merge(staff);
            tr.commit();
            System.out.println("Nhân viên đã được xóa khỏi dự án.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean updateProject(Long projectId, String name, String description,
                                 LocalDate startDate, LocalDate endDate, ProjectStatus status) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Project project = em.find(Project.class, projectId);
            if (project == null) {
                System.out.println("Không tìm thấy dự án.");
                return false;
            }
            project.setName(name);
            project.setDescription(description);
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            project.setStatus(status);
            project.setUpdateAt(LocalDate.now());
            em.merge(project);
            tr.commit();
            System.out.println("Dự án đã được cập nhật.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

}
