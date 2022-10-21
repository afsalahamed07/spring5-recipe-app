package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;
    private Integer perpTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;

    // TODO: 2022-10-21 add difficulty
    // private Difficulty difficulty

    @Lob // binary large object
    private Byte[] image;

    // when deleting the recipe notes will get deleted (cascade)
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;
}
