package backend.ports.driving;

import backend.domain.model.Question;

import java.util.List;

public interface QuestionService {
    Question addAskedQuestion(Question askedQuestion);
    Question addAnswerToQuestion(Question answeredAskedQuestion);
    List<Question> getAllAnsweredQuestions();
    List<Question> getAllUnansweredQuestions();
    void deleteAskedQuestion(Long id);
}
