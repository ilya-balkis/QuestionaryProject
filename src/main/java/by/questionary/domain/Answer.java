package by.questionary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Data(staticConstructor = "of")
@NoArgsConstructor(force = true)
public class Answer {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "test_result_id")
    private TestResult testResult;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = true)
    @Length(max = 2048, message = "Message must not be longer than 2048 symbols")
    private String answer;
}
