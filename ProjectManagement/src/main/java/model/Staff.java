package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Staff")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Staff {
    @Id
    @Column(name = "staff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private Role role;
    private String profilePicture;
    private LocalDate joinedDate;

    @Enumerated(EnumType.ORDINAL)
    private StaffStatus status; // ✅ Thêm dòng này

    @ElementCollection
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name="staff_id"))
    @Column(name = "phone", nullable = false)
    private Set<String> phones;

    @ManyToMany(mappedBy = "assignedStaffs")
    private Set<Project> projects;
}
