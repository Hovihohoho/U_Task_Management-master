package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Attachment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Attachment {
    @Id
    @Column(name = "attachment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path;
    private double size;
    private LocalDate createAt;
    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
}
