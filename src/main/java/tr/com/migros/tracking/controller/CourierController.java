package tr.com.migros.tracking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tr.com.migros.tracking.controller.dto.LocationDto;
import tr.com.migros.tracking.controller.dto.TotalDistanceResponse;
import tr.com.migros.tracking.service.CourierService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(value = "/v1/courier",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Courier API", description = "Manages courier processes")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping(value = "/{courierId}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Log courier location",
            description = "Log courier location and checks if there is any entrance")
    @ApiResponse(responseCode = "201", description = "Processed successfully.")
    @ApiResponse(responseCode = "400", description = "One or more field is/are invalid.")
    public void logLocation(@PathVariable @NotNull final Long courierId, @Valid @RequestBody final LocationDto locationDto) {
        courierService.updateLocation(courierId, locationDto);
    }

    @GetMapping(value = "/{courierId}/distance", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Returns the courier total distance",
            description = "Returns calculated total distance the courier travelled")
    @ApiResponse(responseCode = "201", description = "Processed successfully.")
    @ApiResponse(responseCode = "400", description = "One or more field is/are invalid.")
    public TotalDistanceResponse getTotalDistance(@PathVariable @NotNull final Long courierId) {
        return TotalDistanceResponse.builder()
                .totalDistance(courierService.getCourierTotalDistance(courierId))
                .build();
    }
}
