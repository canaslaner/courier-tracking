package tr.com.migros.tracking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import tr.com.migros.tracking.model.base.ModifiableEntity;

import javax.persistence.*;

@Entity
@Table(name = "courier")
@Getter
@Setter
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
public class Courier extends ModifiableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courierSequenceGenerator")
    @SequenceGenerator(name = "courierSequenceGenerator", initialValue = 10, allocationSize = 100)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "latest_latitude", nullable = false)
    private double latestLatitude = 0;

    @Column(name = "latest_longitude", nullable = false)
    private double latestLongitude = 0;

    @Column(name = "total_distance")
    private double totalDistance = 0;

}

