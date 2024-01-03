package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.transformer.QuestionTransformer;
import backend.domain.model.Question;
import backend.ports.driven.QuestionRepository;
import backend.adapters.driven.model.QuestionJpa;
import backend.adapters.driven.repository.jpa.QuestionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionRepositoryImpl implements QuestionRepository {
    private final QuestionJpaRepository questionJpaRepository;
    private final QuestionTransformer questionTransformer;

    public QuestionRepositoryImpl(
            QuestionJpaRepository questionJpaRepository,
            QuestionTransformer questionTransformer
    ) {
        this.questionJpaRepository = questionJpaRepository;
        this.questionTransformer = questionTransformer;
    }

    @Override
    public Question save(Question question) {
        QuestionJpa questionJpa = questionTransformer.domainToEntity(question);
        QuestionJpa savedQuestion = questionJpaRepository.save(questionJpa);
        return questionTransformer.entityToDomain(savedQuestion);
    }

    @Override
    public Optional<Question> findById(Long id) {
        Optional<QuestionJpa> questionJpa = questionJpaRepository.findById(id);
        return questionJpa.map(questionTransformer::entityToDomain);
    }

    @Override
    public List<Question> findAll() {
        List<QuestionJpa> allQuestionsJpa = questionJpaRepository.findAll();
        return allQuestionsJpa.stream().map(questionTransformer::entityToDomain
        ).toList();
    }

    @Override
    public List<Question> findAllAnswered() {
        List<QuestionJpa> allQuestionsJpa = questionJpaRepository.findAllAnswered();
        return allQuestionsJpa.stream().map(questionTransformer::entityToDomain
        ).toList();
    }

    @Override
    public List<Question> findAllUnanswered() {
        List<QuestionJpa> allQuestionsJpa = questionJpaRepository.findAllUnanswered();
        return allQuestionsJpa.stream().map(questionTransformer::entityToDomain
        ).toList();
    }

    @Override
    public void deleteById(Long id) {
        questionJpaRepository.deleteById(id);
    }
}
