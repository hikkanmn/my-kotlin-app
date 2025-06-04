package ru.hspm.vikas_recipe_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ru.hspm.vikas_recipe_app.data.model.Recipe

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipe = intent.getSerializableExtra("recipe") as Recipe

        val recipeImage = findViewById<ImageView>(R.id.recipeImage)
        val recipeName = findViewById<TextView>(R.id.recipeName)
        val recipeTime = findViewById<TextView>(R.id.recipeTime)
        val recipeServings = findViewById<TextView>(R.id.recipeServings)
        val recipeIngredients = findViewById<TextView>(R.id.recipeIngredients)
        val recipeInstructions = findViewById<TextView>(R.id.recipeInstructions)

        Glide.with(this)
            .load(recipe.image)
            .into(recipeImage)

        recipeName.text = recipe.title
        recipeTime.text = "Время приготовления: ${recipe.readyInMinutes} минут"
        recipeServings.text = "Количество порций: ${recipe.servings}"
        
        val ingredientsText = recipe.ingredients.joinToString("\n") { 
            "- ${it.amount} ${it.unit} ${it.name}"
        }
        recipeIngredients.text = ingredientsText
        
        recipeInstructions.text = recipe.instructions
    }
} 