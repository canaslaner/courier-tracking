package tr.com.migros.tracking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.migros.tracking.util.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class DistanceServiceImpl implements DistanceService {

    /**
     * Calculates distance in meters between two geolocations. Haversine formula is applied to find the result.
     * To dive deeper check for
     * <a href="https://en.wikipedia.org/wiki/Haversine_formula">https://en.wikipedia.org/wiki/Haversine_formula</a> and
     * <a href="https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/">https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/</a>
     * <p>
     * haversine formula :
     * a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
     * c = 2 ⋅ atan2( √a, √(1−a) )
     * d = R ⋅ c
     *
     * @param refLatitude     - the latitude of the first location
     * @param refLongitude    - the longitude of the first location
     * @param targetLatitude  - the latitude of the second location
     * @param targetLongitude - the longitude of the second location
     * @return - distance between two locations, in meters
     */
    @Override
    public double calculateDistance(final double refLatitude, final double refLongitude, final double targetLatitude,
                                    final double targetLongitude) {
        if (refLatitude == targetLatitude && refLongitude == targetLongitude) {
            return 0;
        }

        final double latitudeDiff = Math.toRadians(targetLatitude - refLatitude);
        final double longitudeDiff = Math.toRadians(targetLongitude - refLongitude);

        final double a = Math.pow(Math.sin(latitudeDiff / 2), 2) +
                Math.pow(Math.sin(longitudeDiff / 2), 2)
                        * Math.cos(Math.toRadians(refLatitude))
                        * Math.cos(Math.toRadians(targetLatitude));
        final double c = 2 * Math.asin(Math.sqrt(a));

        return BigDecimal.valueOf(Constants.EARTH_RADIUS_IN_METERS * c)
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}