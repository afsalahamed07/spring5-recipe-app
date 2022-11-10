package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        UnitOfMeasure teaspoon;
        UnitOfMeasure whole;
        UnitOfMeasure tablespoon;

        Category italian;

        // Teaspoon
        if (unitOfMeasureRepository.findByDescription("Teaspoon").isPresent()) {
            teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        } else {
            teaspoon = new UnitOfMeasure();
            teaspoon.setDescription("Teaspoon");
            unitOfMeasureRepository.save(teaspoon);
        }

        // Whole
        if (unitOfMeasureRepository.findByDescription("Whole").isPresent()) {
            whole = unitOfMeasureRepository.findByDescription("Whole").get();
        } else {
            whole = new UnitOfMeasure();
            whole.setDescription("Whole");
            unitOfMeasureRepository.save(whole);
        }

        // Tablespoon
        if (unitOfMeasureRepository.findByDescription("Tablespoon").isPresent()) {
            tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        } else {
            tablespoon = new UnitOfMeasure();
            tablespoon.setDescription("Tablespoon");
            unitOfMeasureRepository.save(whole);
        }

        if (categoryRepository.findByDescription("Italian").isPresent()) {
            italian = categoryRepository.findByDescription("Italian").get();
        } else {
            italian = new Category();
            italian.setDescription("Italian");
            categoryRepository.save(italian);
        }


        Ingredient avocado = new Ingredient();
        avocado.setDescription("Ripe Avocado");
        avocado.setUnitOfMeasure(whole);
        avocado.setAmount(new BigDecimal(2));

        Ingredient salt = new Ingredient();
        salt.setDescription("Kosher Salt");
        salt.setUnitOfMeasure(teaspoon);
        salt.setAmount(new BigDecimal(1/4));

        Ingredient lime = new Ingredient();
        lime.setDescription("Lime or Lemon Juice");
        lime.setUnitOfMeasure(tablespoon);
        lime.setAmount(new BigDecimal(1));

        Ingredient chilli = new Ingredient();
        chilli.setDescription("Serrano Chilli");
        chilli.setUnitOfMeasure(whole);
        chilli.setAmount(new BigDecimal(2));

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("Cilantro finely choped");
        cilantro.setUnitOfMeasure(tablespoon);
        cilantro.setAmount(new BigDecimal(2));

        Ingredient onion = new Ingredient();
        onion.setDescription("Minced Onion");
        onion.setUnitOfMeasure(tablespoon);
        onion.setAmount(new BigDecimal(4));

        Notes notes = new Notes();
        notes.setRecipeNotes("Dont try this at home");

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPerpTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(4);
        guacamole.setCategories(new HashSet<>(Arrays.asList(
                italian
        )));
        guacamole.setNotes(notes);
        guacamole.setDifficulty(Difficulty.HARD);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole");

        guacamole.setIngredients(new HashSet<>(Arrays.asList(
                avocado, salt, lime, chilli, cilantro, onion
        )));

        // directions are stored in static/recipe_directions
        Path guacamoleDir = Path.of("recipe-data/src/main/resources/recipe_directions/guacamole.txt");
        guacamole.setDirections(Files.readString(guacamoleDir));

        recipeRepository.save(guacamole);
    }
}
