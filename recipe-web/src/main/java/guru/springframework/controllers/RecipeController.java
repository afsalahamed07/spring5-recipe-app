package guru.springframework.controllers;

import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private RecipeService recipeService;

    @RequestMapping({"", "/", "/index", "index.html"})
    public String listRecipes(Model model) {

        model.addAttribute("recipes", recipeService.getRecipes());

        return "recipes/index";
    }
}
