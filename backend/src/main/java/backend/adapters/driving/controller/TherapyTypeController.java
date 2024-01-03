package backend.adapters.driving.controller;

import backend.adapters.driving.model.TherapyTypeDto;
import backend.adapters.driving.transformer.TherapyTypeDtoTransformer;
import backend.domain.model.TherapyType;
import backend.ports.driving.TherapyTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/therapy-type")
@CrossOrigin
public class TherapyTypeController {
    private final TherapyTypeService therapyTypeService;
    private final TherapyTypeDtoTransformer therapyTypeTransformer;

    public TherapyTypeController(
            TherapyTypeService therapyTypeService,
            TherapyTypeDtoTransformer therapyTypeTransformer
    ) {
        this.therapyTypeService = therapyTypeService;
        this.therapyTypeTransformer = therapyTypeTransformer;
    }

    @PostMapping("/add")
    TherapyTypeDto addTherapyType(@RequestBody TherapyTypeDto therapyTypeDto) {
        TherapyType therapyType = therapyTypeTransformer.toDomain(therapyTypeDto);
        TherapyType returnValue = therapyTypeService.addTherapyType(therapyType);
        return therapyTypeTransformer.fromDomain(returnValue);
    }

    @GetMapping("/fetch")
    List<TherapyTypeDto> getAllTherapyTypes() {
        List<TherapyType> allTherapyTypes = therapyTypeService.getAllTherapyTypes();
        return allTherapyTypes.stream().map(
                therapyTypeTransformer::fromDomain
        ).toList();
    }

    @GetMapping("/fetch/{id}")
    TherapyTypeDto getTherapyTypeById(@PathVariable Long id) {
        TherapyType therapyType = therapyTypeService.getTherapyTypeById(id);
        return therapyTypeTransformer.fromDomain(therapyType);
    }

}
