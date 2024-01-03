package backend.adapters.driven.transformer;

import backend.domain.model.Question;
import backend.adapters.driven.model.QuestionJpa;
import org.springframework.stereotype.Component;

@Component
public class QuestionTransformer implements Transformer<Question, QuestionJpa> {

    @Override
    public QuestionJpa domainToEntity(Question domain) {
        return new QuestionJpa(
                domain.getId(),
                domain.getQuestion(),
                domain.getAnswer()
        );
    }

    @Override
    public Question entityToDomain(QuestionJpa entity) {
        return new Question(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer()
        );
    }
}
