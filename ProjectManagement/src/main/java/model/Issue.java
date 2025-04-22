package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Issue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Issue {
    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate createAt;
    private LocalDate updateAt;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff assignee;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToMany(mappedBy = "issues")
    private Set<Label> labels;

    @ToString.Exclude
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private Set<Comment> comments;
}
