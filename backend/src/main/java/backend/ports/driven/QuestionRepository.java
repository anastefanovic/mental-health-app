package backend.ports.driven;

import backend.domain.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(Long id);
    List<Question> findAll();
    List<Question> findAllAnswered();
    List<Question> findAllUnanswered();
    void deleteById(Long id);
}
