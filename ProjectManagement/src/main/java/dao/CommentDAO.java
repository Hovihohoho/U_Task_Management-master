package dao;

import model.Comment;
import model.Issue;
import model.Staff;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class CommentDAO {
    private EntityManager em;
    public CommentDAO(EntityManager em) {
        this.em = em;
    }
    public Comment getCommentById(Long id) {
        return em.find(Comment.class, id);
    }
    public List<Comment> getAllComments() {
        String jpql = "SELECT c FROM Comment c";
        return em.createQuery(jpql, Comment.class).getResultList();
    }
    public List<Comment> getCommentsByIssueId(Long issueId) {
        String jpql = "SELECT c FROM Comment c WHERE c.issue.id = :issueId";
        return em.createQuery(jpql, Comment.class)
                .setParameter("issueId", issueId)
                .getResultList();
    }
    public boolean createComment(String content, Long issueId, Long staffId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Issue issue = em.find(Issue.class, issueId);
            if (issue == null) {
                System.out.println("Không tìm thấy vấn đề.");
                return false;
            }
            Staff staff = em.find(Staff.class, staffId);
            if (staff == null) {
                System.out.println("Không tìm thấy nhân viên.");
                return false;
            }
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreateAt(LocalDate.now());
            comment.setUpdateAt(LocalDate.now());
            comment.setIssue(issue);
            comment.setStaff(staff);
            em.persist(comment);
            tr.commit();
            System.out.println("Bình luận đã được tạo.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean updateComment(Long commentId, String newContent) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Comment comment = em.find(Comment.class, commentId);
            if (comment == null) {
                System.out.println("Không tìm thấy bình luận.");
                return false;
            }
            comment.setContent(newContent);
            comment.setUpdateAt(LocalDate.now());
            em.merge(comment);
            tr.commit();
            System.out.println("Bình luận đã được cập nhật.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean deleteComment(Long commentId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Comment comment = em.find(Comment.class, commentId);
            if (comment == null) {
                System.out.println("Không tìm thấy bình luận.");
                return false;
            }
            em.remove(comment);
            tr.commit();
            System.out.println("Bình luận đã được xóa.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

}
