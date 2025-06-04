package ru.hspm.vikas_recipe_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.hspm.vikas_recipe_app.ui.adapter.RecipeAdapter
import ru.hspm.vikas_recipe_app.ui.viewmodel.RecipeViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var searchEditText: TextInputEditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        
        setupViews()
        setupViewModel()
        setupRecyclerView()
        setupSearch()
        observeViewModel()
    }

    private fun setupViews() {
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(
            onRecipeClick = { recipe ->
                val intent = Intent(this, RecipeDetailsActivity::class.java).apply {
                    putExtra("recipe", recipe)
                }
                startActivity(intent)
            },
            onFavoriteClick = { recipe ->
                if (viewModel.isFavorite(recipe)) {
                    viewModel.removeFromFavorites(recipe)
                    Toast.makeText(this, "Рецепт удален из избранного", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addToFavorites(recipe)
                    Toast.makeText(this, "Рецепт добавлен в избранное", Toast.LENGTH_SHORT).show()
                }
            }
        )
        
        recipesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = recipeAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        searchEditText.setOnEditorActionListener { _, _, _ ->
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchRecipes(query)
            }
            true
        }
    }

    private fun observeViewModel() {
        viewModel.recipes.observe(this) { recipes ->
            recipeAdapter.updateRecipes(recipes)
        }
        
        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun openSettings(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun openDayFood(view: View) {
        startActivity(Intent(this, DayFoodActivity::class.java))
    }

    fun openFavorites(view: View) {
        startActivity(Intent(this, FavoritesActivity::class.java))
    }
}