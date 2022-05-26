package tr.com.migros.tracking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import tr.com.migros.tracking.model.base.ModifiableEntity;

import javax.persistence.*;

@Entity
@Table(name = "store")
@Getter
@Setter
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
public class Store extends ModifiableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storeSequenceGenerator")
    @SequenceGenerator(name = "storeSequenceGenerator", initialValue = 10, allocationSize = 100)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

}

