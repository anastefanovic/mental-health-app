package backend.adapters.driven.repository.jpa;

import backend.adapters.driven.model.AppointmentJpa;
import backend.adapters.driven.model.TherapistJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentJpaRepository extends CrudRepository<AppointmentJpa, Long> {
    List<AppointmentJpa> findAllByTherapistIdAndSessionDate(Long therapistId, LocalDate sessionDate);
    List<AppointmentJpa> findAllByTherapistId(Long therapistId);
    List<AppointmentJpa> findAllByClientId(Long clientId);

    @Query(
            "SELECT DISTINCT therapist FROM AppointmentJpa AS a " +
                    "WHERE a.client.id = :clientId AND (a.sessionDate < :date " +
                    "OR (a.sessionDate = :date AND a.sessionTime < :time))"
    )
    List<TherapistJpa> findAllByPastAppointmentWithClient(
            @Param("clientId") Long clientId,
            @Param("date")LocalDate date,
            @Param("time") LocalTime time
    );
}
