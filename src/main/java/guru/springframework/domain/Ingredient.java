package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;
    private BigDecimal amount;

    @ManyToOne // not sure whether this correct
    // thinking one to one
    private Recipe recipe;

    @OneToOne //default fetch behaviour is eager
    private UnitOfMeasure unitOfMeasure;
}
