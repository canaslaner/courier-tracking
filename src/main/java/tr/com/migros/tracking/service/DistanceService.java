package tr.com.migros.tracking.service;

public interface DistanceService {
    double calculateDistance(double refLatitude, double refLongitude, double targetLatitude, double targetLongitude);
}
