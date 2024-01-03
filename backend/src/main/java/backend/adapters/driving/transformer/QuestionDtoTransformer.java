package backend.adapters.driving.transformer;

import backend.adapters.driving.model.QuestionDto;
import backend.domain.model.Question;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class QuestionDtoTransformer implements Transformer<Question, QuestionDto> {

    @Override
    public QuestionDto fromDomain(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getQuestion(),
                question.getAnswer()
        );
    }

    @Override
    public Question toDomain(QuestionDto questionDto) {
        return new Question(
                questionDto.getId(),
                Objects.requireNonNull(questionDto.getQuestion()),
                questionDto.getAnswer()
        );
    }
}
