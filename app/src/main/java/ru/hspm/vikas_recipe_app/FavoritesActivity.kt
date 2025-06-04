package ru.hspm.vikas_recipe_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hspm.vikas_recipe_app.ui.adapter.RecipeAdapter
import ru.hspm.vikas_recipe_app.ui.viewmodel.RecipeViewModel

class FavoritesActivity : AppCompatActivity() {
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var emptyFavoritesText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recipesRecyclerView = findViewById(R.id.recipesRecyclerView)
        emptyFavoritesText = findViewById(R.id.emptyFavoritesText)

        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        recipeAdapter = RecipeAdapter(
            onRecipeClick = { recipe ->
                val intent = Intent(this, RecipeDetailsActivity::class.java)
                intent.putExtra("recipe", recipe)
                startActivity(intent)
            },
            onFavoriteClick = { recipe ->
                viewModel.removeFromFavorites(recipe)
            }
        )

        recipesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            adapter = recipeAdapter
        }

        viewModel.favorites.observe(this) { favorites ->
            recipeAdapter.updateRecipes(favorites)
            emptyFavoritesText.visibility = if (favorites.isEmpty()) TextView.VISIBLE else TextView.GONE
        }
    }
    fun goBack(view: View) {
        finish() // Закрывает вторую активность и возвращает на первую
    }
}