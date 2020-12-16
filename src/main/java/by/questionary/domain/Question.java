package by.questionary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "question")
@Data(staticConstructor = "of")
@NoArgsConstructor(force = true)
public class Question {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(nullable = false)
    private int questionNumber;

    @Column(nullable = false)
    private int pageNumber;

    @Column(nullable = false)
    @NotBlank(message = "Please fill field title")
    @Length(min = 1, max = 300, message = "Title must be 1 or 300 symbols")
    private String questionTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(nullable = false)
    private boolean obligatoryAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Answer> answers;

}
