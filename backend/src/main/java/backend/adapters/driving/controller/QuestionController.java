package backend.adapters.driving.controller;

import backend.adapters.driving.model.QuestionDto;
import backend.adapters.driving.transformer.QuestionDtoTransformer;
import backend.domain.model.Question;
import backend.ports.driving.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionDtoTransformer questionTransformer;

    public QuestionController(
            QuestionService questionService,
            QuestionDtoTransformer questionTransformer
    ) {
        this.questionService = questionService;
        this.questionTransformer = questionTransformer;
    }

    @PostMapping("/add")
    QuestionDto addAskedQuestion(@RequestBody QuestionDto questionDto) {
        Question askedQuestion = questionTransformer.toDomain(questionDto);
        Question returnValue = questionService.addAskedQuestion(askedQuestion);
        return questionTransformer.fromDomain(returnValue);
    }

    @PutMapping("/answer")
    QuestionDto addAnswerToAskedQuestion(@RequestBody QuestionDto questionDto) {
        Question question = questionTransformer.toDomain(questionDto);
        Question returnValue = questionService.addAnswerToQuestion(question);
        return questionTransformer.fromDomain(returnValue);
    }

    @GetMapping("/answered/get")
    List<QuestionDto> getAllAnsweredQuestions() {
        List<Question> answeredQuestions = questionService.getAllAnsweredQuestions();
        return answeredQuestions.stream().map(
                questionTransformer::fromDomain
        ).toList();
    }

    @GetMapping("/unanswered/get")
    List<QuestionDto> getAllUnansweredQuestions() {
        List<Question> unansweredQuestions = questionService.getAllUnansweredQuestions();
        return unansweredQuestions.stream().map(
                questionTransformer::fromDomain
        ).toList();
    }

    @DeleteMapping("/{id}")
    public void deleteAskedQuestion(@PathVariable Long id) {
        questionService.deleteAskedQuestion(id);
    }
}
