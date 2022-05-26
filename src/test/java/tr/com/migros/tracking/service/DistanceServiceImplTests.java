package tr.com.migros.tracking.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DistanceServiceImplTests {

    @InjectMocks
    private DistanceServiceImpl distanceService;

    @Test
    void testCalculateDistance_whenRefAndTargetAreTheSame_thenShouldReturnZero() {
        // given && when
        final var result = distanceService.calculateDistance(40.9923307, 29.1244229, 40.9923307, 29.1244229);

        // then
        Assertions.assertEquals(0, result);
    }

    @Test
    void testCalculateDistance_whenRefAndTargetAreValid_thenShouldReturnDistance() {
        // given
        final var atasehirMigrosLatitude = 40.9923307;
        final var atasehirMigrosLongitude = 29.1244229;
        final var novadaMigrosLatitude = 40.986106;
        final var novadaMigrosLongitude = 29.1161293;

        // when
        final var result = distanceService.calculateDistance(atasehirMigrosLatitude, atasehirMigrosLongitude,
                novadaMigrosLatitude, novadaMigrosLongitude);

        // then
        Assertions.assertEquals(981.66, result);
    }
}
