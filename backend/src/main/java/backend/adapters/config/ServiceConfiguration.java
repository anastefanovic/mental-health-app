package backend.adapters.config;

import backend.domain.service.*;
import backend.ports.driven.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    DomainUserService userService(
            AdminRepository adminRepository,
            ClientRepository clientRepository,
            TherapistRepository therapistRepository
    ) {
        return new DomainUserService(
                adminRepository,
                clientRepository,
                therapistRepository
        );
    }

    @Bean
    DomainReviewService reviewService(
            ReviewRepository reviewRepository
    ) {
        return new DomainReviewService(
                reviewRepository
        );
    }

    @Bean
    DomainQuestionService questionService(
            QuestionRepository questionRepository
    ) {
        return new DomainQuestionService(
            questionRepository
        );
    }

    @Bean
    DomainAppointmentService appointmentService(
            AppointmentRepository appointmentRepository
    ) {
        return new DomainAppointmentService(
            appointmentRepository
        );
    }

    @Bean
    DomainTherapyTypeService therapyTypeService(
            TherapyTypeRepository therapyTypeRepository
    ) {
        return new DomainTherapyTypeService(
                therapyTypeRepository
        );
    }
}
