package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate createAt;
    private LocalDate updateAt;

    @Enumerated(EnumType.ORDINAL)
    private ProjectStatus status;

    @ManyToMany
    @JoinTable(
            name = "project_staff",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<Staff> assigned;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Issue> issues;
}
