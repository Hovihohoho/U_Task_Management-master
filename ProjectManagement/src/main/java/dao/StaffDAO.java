package dao;

import model.Project;
import model.Staff;
import model.Role;
import model.StaffStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class StaffDAO {
    private EntityManager em;
    public StaffDAO(EntityManager em) {
        this.em = em;
    }
    public List<Staff> getAllStaff() {
        try {
            TypedQuery<Staff> query = em.createQuery("SELECT p FROM Staff p", Staff.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Staff> getStaffByName(String name) {
        try {
            TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.name = :name", Staff.class);
            query.setParameter("name", name);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Staff getStaffById(Long staffId) {
        try {
            return em.find(Staff.class, staffId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean Signin(String email, String password) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TypedQuery<Staff> query = em.createQuery(
                    "SELECT s FROM Staff s WHERE s.email = :email AND s.password = :password",
                    Staff.class
            );
            query.setParameter("email", email);
            query.setParameter("password", password);
            Staff staff = query.getSingleResult();
            tr.commit();
            return staff != null;
        } catch (NoResultException e) {
            tr.rollback();
            System.out.println("Đăng nhập thất bại: Không tìm thấy email");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean SignUp(String name, String email, String password, Role role, String profilePicture, LocalDate joinedDate, StaffStatus status, Set<String> phones) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TypedQuery<Long> emailCheckQuery = em.createQuery(
                    "SELECT COUNT(s) FROM Staff s WHERE s.email = :email",
                    Long.class
            );
            emailCheckQuery.setParameter("email", email);
            Long count = emailCheckQuery.getSingleResult();
            if (count > 0) {
                System.out.println("Email đã tồn tại, vui lòng nhập email khác.");
                return false;
            }
            Staff newStaff = new Staff();
            newStaff.setName(name);
            newStaff.setEmail(email);
            newStaff.setPassword(password);
            newStaff.setRole(role);
            newStaff.setProfilePicture(profilePicture);
            newStaff.setJoinedDate(joinedDate);
            newStaff.setStatus(status);
            newStaff.setPhones(phones);
            em.persist(newStaff);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean update(Long staffId, String name, Role role, String profilePicture, LocalDate joinedDate, StaffStatus status, Set<String> phones) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Staff staff = em.find(Staff.class, staffId);
            if (staff == null) {
                System.out.println("Không tìm thấy nhân viên");
                return false;
            }
            if (name != null && !name.isEmpty()) {
                staff.setName(name);
            }
            if (role != null) {
                staff.setRole(role);
            }
            if (profilePicture != null && !profilePicture.isEmpty()) {
                staff.setProfilePicture(profilePicture);
            }
            if (joinedDate != null) {
                staff.setJoinedDate(joinedDate);
            }
            if (status != null) {
                staff.setStatus(status);
            }
            if (phones != null && !phones.isEmpty()) {
                staff.setPhones(phones);
            }
            em.merge(staff);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean deleteStaff(Long staffId) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Staff staff = em.find(Staff.class, staffId);
            if (staff == null) {
                System.out.println("Không tìm thấy nhân viên.");
                return false;
            }
            for (Project project : staff.getProjects()) {
                project.getAssigned().remove(staff);
                em.merge(project);
            }
            em.remove(staff);
            tr.commit();
            System.out.println("Nhân viên đã được xóa.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.email = :email", Staff.class);
            query.setParameter("email", email);
            Staff staff = query.getSingleResult();
            if (staff == null) {
                System.out.println("Không tìm thấy nhân viên với email: " + email);
                return false;
            }
            if (!staff.getPassword().equals(oldPassword)) {
                System.out.println("Mật khẩu cũ không chính xác.");
                return false;
            }
            staff.setPassword(newPassword);
            em.merge(staff);
            tr.commit();

            return true;
        } catch (NoResultException e) {
            System.out.println("Không tìm thấy nhân viên với email: " + email);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
            return false;
        }
    }

}
