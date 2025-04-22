package dao;

import model.Issue;
import model.Project;
import model.Staff;
import model.Priority;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class IssueDAO {
    private EntityManager em;
    public IssueDAO(EntityManager em) {
        this.em = em;
    }
    public Issue getIssueById(Long id) {
        return em.find(Issue.class, id);
    }
    public List<Issue> getAllIssues() {
        String jpql = "SELECT i FROM Issue i";
        return em.createQuery(jpql, Issue.class).getResultList();
    }
    public List<Issue> getIssuesByTitle(String title) {
        String jpql = "SELECT i FROM Issue i WHERE i.title LIKE :title";
        return em.createQuery(jpql, Issue.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }
    public boolean createIssue(String title, String description, Priority priority, Long assigneeId, Long projectId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Staff assignee = em.find(Staff.class, assigneeId);
            if (assignee == null) {
                System.out.println("Không tìm thấy nhân viên.");
                return false;
            }
            Project project = em.find(Project.class, projectId);
            if (project == null) {
                System.out.println("Không tìm thấy dự án.");
                return false;
            }
            Issue issue = new Issue();
            issue.setTitle(title);
            issue.setDescription(description);
            issue.setCreateAt(LocalDate.now());
            issue.setUpdateAt(LocalDate.now());
            issue.setPriority(priority);
            issue.setAssignee(assignee);
            issue.setProject(project);
            em.persist(issue);
            tr.commit();
            System.out.println("Vấn đề đã được tạo.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

    public boolean updateIssue(Long issueId, String title, String description, Priority priority, Long assigneeId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Issue issue = em.find(Issue.class, issueId);
            if (issue == null) {
                System.out.println("Không tìm thấy vấn đề.");
                return false;
            }
            issue.setTitle(title);
            issue.setDescription(description);
            issue.setPriority(priority);
            if (assigneeId != null) {
                Staff assignee = em.find(Staff.class, assigneeId);
                if (assignee == null) {
                    System.out.println("Không tìm thấy nhân viên.");
                    return false;
                }
                issue.setAssignee(assignee);
            }
            issue.setUpdateAt(LocalDate.now());
            em.merge(issue);
            tr.commit();
            System.out.println("Vấn đề đã được cập nhật.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

}
