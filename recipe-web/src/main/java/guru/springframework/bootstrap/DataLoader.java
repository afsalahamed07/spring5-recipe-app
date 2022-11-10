package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>();

        UnitOfMeasure teaspoon;
        UnitOfMeasure whole;
        UnitOfMeasure tablespoon;

        Category italian;

        Recipe guacamole = new Recipe();

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
        avocado.setRecipe(guacamole);

        Ingredient salt = new Ingredient();
        salt.setDescription("Kosher Salt");
        salt.setUnitOfMeasure(teaspoon);
        salt.setAmount(new BigDecimal(1/4));
        salt.setRecipe(guacamole);

        Ingredient lime = new Ingredient();
        lime.setDescription("Lime or Lemon Juice");
        lime.setUnitOfMeasure(tablespoon);
        lime.setAmount(new BigDecimal(1));
        lime.setRecipe(guacamole);

        Ingredient chilli = new Ingredient();
        chilli.setDescription("Serrano Chilli");
        chilli.setUnitOfMeasure(whole);
        chilli.setAmount(new BigDecimal(2));
        chilli.setRecipe(guacamole);

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("Cilantro finely chopped");
        cilantro.setUnitOfMeasure(tablespoon);
        cilantro.setAmount(new BigDecimal(2));
        cilantro.setRecipe(guacamole);

        Ingredient onion = new Ingredient();
        onion.setDescription("Minced Onion");
        onion.setUnitOfMeasure(tablespoon);
        onion.setAmount(new BigDecimal(4));
        onion.setRecipe(guacamole);

        Notes notes = new Notes();
        notes.setRecipeNotes("Dont try this at home");
        notes.setRecipe(guacamole);

        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPerpTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(4);
        guacamole.setCategories(new HashSet<>(List.of(
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
        try {
            guacamole.setDirections(Files.readString(guacamoleDir));
        } catch (IOException ex) {
            System.out.println("IO exception occurred while reading recipe direction;");
            ex.printStackTrace();
        }

        recipes.add(guacamole);

        return  recipes;
    }

}
