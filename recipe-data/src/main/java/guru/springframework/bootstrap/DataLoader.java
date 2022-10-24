package guru.springframework.bootstrap;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public void run(String... args) throws Exception {

        UnitOfMeasure teaspoon;

        if (unitOfMeasureRepository.findByDescription("Teaspoon").isPresent()) {
            teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        } else {
            teaspoon = new UnitOfMeasure();
            teaspoon.setDescription("Teaspoon");
            unitOfMeasureRepository.save(teaspoon);
        }

        Ingredient avocado = new Ingredient();
        avocado.setDescription("Ripe Avocado");
        avocado.setAmount(new BigDecimal(2));

        Ingredient kosherSalt = new Ingredient();
        kosherSalt.setDescription("Kosher Salt");
        kosherSalt.setUnitOfMeasure(teaspoon);
        kosherSalt.setAmount(new BigDecimal(1/4));

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPerpTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(4);

        // directions are stored in static/recipe_directions
        Path guacamoleDir = Path.of("recipe-data/src/main/resources/recipe_directions/guacamole.txt");
        guacamole.setDirections(Files.readString(guacamoleDir));
    }
}
