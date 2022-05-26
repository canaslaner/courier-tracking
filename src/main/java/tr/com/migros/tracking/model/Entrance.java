package tr.com.migros.tracking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import tr.com.migros.tracking.model.base.CreatableEntity;

import javax.persistence.*;

@Entity
@Table(name = "entrance")
@Getter
@Setter
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
public class Entrance extends CreatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entranceSequenceGenerator")
    @SequenceGenerator(name = "entranceSequenceGenerator", initialValue = 10, allocationSize = 100)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

}

