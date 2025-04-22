package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Label")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Label {
    @Id
    @Column(name = "label_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String color;
    private LocalDate createAt;
    private LocalDate updateAt;

    @ManyToOne
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "issues_labels",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    private Set<Issue> issues;
}
