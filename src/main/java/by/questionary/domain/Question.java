package by.questionary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@Data(staticConstructor = "of")
@NoArgsConstructor(force = true)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    @Column(nullable = false)
    private Test test;

    @Column(nullable = false)
    private int questionNumber;

    @Column(nullable = false)
    private String questionTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private QuestionType questionType;

    @Column(nullable = false)
    private boolean obligatoryAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Answer> answers;

}
