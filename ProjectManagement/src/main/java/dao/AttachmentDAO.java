package dao;

import model.Attachment;
import model.Issue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.List;

public class AttachmentDAO {
    private EntityManager em;
    public AttachmentDAO(EntityManager em) {
        this.em = em;
    }
    public boolean createAttachment(String name, String path, double size, Long issueId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Issue issue = em.find(Issue.class, issueId);
            if (issue == null) {
                System.out.println("Không tìm thấy vấn đề.");
                return false;
            }
            Attachment attachment = new Attachment();
            attachment.setName(name);
            attachment.setPath(path);
            attachment.setSize(size);
            attachment.setCreateAt(LocalDate.now());
            attachment.setUpdateAt(LocalDate.now());
            attachment.setIssue(issue);
            em.persist(attachment);
            tr.commit();
            System.out.println("Đính kèm đã được tạo.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public List<Attachment> getAllAttachments() {
        String jpql = "SELECT a FROM Attachment a";
        return em.createQuery(jpql, Attachment.class).getResultList();
    }
    public Attachment getAttachmentById(Long id) {
        return em.find(Attachment.class, id);
    }
    public Attachment getAttachmentByName(String name) {
        String jpql = "SELECT a FROM Attachment a WHERE a.name = :name";
        try {
            return em.createQuery(jpql, Attachment.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Không tìm thấy đính kèm với tên: " + name);
            return null;
        }
    }

    public boolean updateAttachment(Long attachmentId, String name, String path, double size) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Attachment attachment = em.find(Attachment.class, attachmentId);
            if (attachment == null) {
                System.out.println("Không tìm thấy đính kèm.");
                return false;
            }
            attachment.setName(name);
            attachment.setPath(path);
            attachment.setSize(size);
            attachment.setUpdateAt(LocalDate.now());
            em.merge(attachment);
            tr.commit();
            System.out.println("Đính kèm đã được cập nhật.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean deleteAttachment(Long attachmentId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Attachment attachment = em.find(Attachment.class, attachmentId);
            if (attachment == null) {
                System.out.println("Không tìm thấy đính kèm.");
                return false;
            }
            em.remove(attachment);
            tr.commit();
            System.out.println("Đính kèm đã được xóa.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

}
