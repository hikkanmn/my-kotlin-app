package ru.hspm.vikas_recipe_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.hspm.vikas_recipe_app.data.model.Recipe
import ru.hspm.vikas_recipe_app.data.api.RecipeApi
import ru.hspm.vikas_recipe_app.utils.Constants
import ru.hspm.vikas_recipe_app.data.api.RetrofitClient

class RecipeViewModel : ViewModel() {
    private val api: RecipeApi = RetrofitClient.api
    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    private val _favorites = MutableLiveData<List<Recipe>>(emptyList())
    private val _loading = MutableLiveData<Boolean>(false)
    private val _error = MutableLiveData<String?>()

    val recipes: LiveData<List<Recipe>> = _recipes
    val favorites: LiveData<List<Recipe>> = _favorites
    val loading: LiveData<Boolean> = _loading
    val error: LiveData<String?> = _error

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = api.searchRecipes(
                    query = query,
                    cuisine = "russian",
                    apiKey = Constants.API_KEY,
                    language = "ru"
                )
                if (response.results.isNotEmpty()) {
                    _recipes.value = response.results
                    _error.value = null
                } else {
                    _recipes.value = emptyList()
                    _error.value = "Рецепты не найдены"
                }
            } catch (e: Exception) {
                _recipes.value = emptyList()
                _error.value = "Ошибка при поиске рецептов: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addToFavorites(recipe: Recipe) {
        val currentFavorites = _favorites.value?.toMutableList() ?: mutableListOf()
        if (!currentFavorites.any { it.id == recipe.id }) {
            currentFavorites.add(recipe.copy(isFavorite = true))
            _favorites.value = currentFavorites
        }
    }

    fun removeFromFavorites(recipe: Recipe) {
        val currentFavorites = _favorites.value?.toMutableList() ?: mutableListOf()
        currentFavorites.removeAll { it.id == recipe.id }
        _favorites.value = currentFavorites
    }

    fun isFavorite(recipe: Recipe): Boolean {
        return _favorites.value?.any { it.id == recipe.id } ?: false
    }
} 