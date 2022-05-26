package tr.com.migros.tracking.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class ModifiableEntity extends CreatableEntity {

    @LastModifiedBy
    @Column(length = 300)
    private String modifiedBy;

    @LastModifiedDate
    @Column
    private Instant lastModifiedDate = Instant.now();
}
