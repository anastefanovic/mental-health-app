package backend.domain.service;

import backend.adapters.driving.exception.ResourceNotFoundException;
import backend.domain.model.Question;
import backend.ports.driving.QuestionService;
import backend.ports.driven.QuestionRepository;

import java.util.List;

public class DomainQuestionService implements QuestionService {
    private final QuestionRepository questionRepository;

    public DomainQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question addAskedQuestion(Question askedQuestion) {
        return questionRepository.save(askedQuestion);
    }

    @Override
    public Question addAnswerToQuestion(Question answeredQuestion) {
        Question question = questionRepository.findById(answeredQuestion.getId())
                .orElseThrow( () -> {
                    throw new ResourceNotFoundException(
                            "Question with id " + answeredQuestion.getId() + " not found"
                    );
                });

        question.setAnswer(answeredQuestion.getAnswer());
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllAnsweredQuestions() {
        return questionRepository.findAllAnswered();
    }

    @Override
    public List<Question> getAllUnansweredQuestions() {
        return questionRepository.findAllUnanswered();
    }

    @Override
    public void deleteAskedQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
