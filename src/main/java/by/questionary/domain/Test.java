package by.questionary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "test")
@Data(staticConstructor = "of")
@NoArgsConstructor(force = true)
public class Test {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Please fill field title")
    @Length(min = 1, max = 60, message = "Title must be 1 or 60 symbols")
    private String title;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfChange;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private TestSpecialization testSpecialization;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TestResult> testResults;
}
