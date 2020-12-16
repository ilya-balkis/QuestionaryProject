package by.questionary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "test_specialization")
@Data(staticConstructor = "of")
@NoArgsConstructor(force = true)
public class TestSpecialization {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(nullable = false)
    private boolean numeratedPage;

    @Column(nullable = false)
    private boolean numeratedQuestion;

    @Column(nullable = false)
    private boolean randomQuestionSequence;

    @Column(nullable = false)
    private boolean indicator;

    @Column(nullable = false)
    private boolean obligatoryAnswer;

    @Column(nullable = false)
    private boolean anonymous;

}
