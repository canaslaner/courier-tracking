package tr.com.migros.tracking.service;

import tr.com.migros.tracking.controller.dto.LocationDto;
import tr.com.migros.tracking.model.Courier;

public interface CourierService {
    Courier updateLocation(long id, LocationDto locationDto);

    double getCourierTotalDistance(long id);
}
