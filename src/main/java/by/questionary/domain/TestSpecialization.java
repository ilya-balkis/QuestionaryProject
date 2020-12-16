package by.questionary.domain;

import javax.persistence.*;

@Entity
@Table(name = "test_specialization")
public class TestSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    @Column(nullable = false)
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
