package backend.adapters.driven.repository.jpa;

import backend.adapters.driven.model.QuestionJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionJpaRepository extends CrudRepository<QuestionJpa, Long> {
    List<QuestionJpa> findAll();
    @Query("SELECT q FROM QuestionJpa AS q WHERE q.answer != null")
    List<QuestionJpa> findAllAnswered();
    @Query("SELECT q FROM QuestionJpa AS q WHERE q.answer = null")
    List<QuestionJpa> findAllUnanswered();
}
