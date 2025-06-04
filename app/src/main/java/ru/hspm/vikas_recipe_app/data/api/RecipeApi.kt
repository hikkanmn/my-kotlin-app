package ru.hspm.vikas_recipe_app.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.hspm.vikas_recipe_app.data.model.Recipe
import ru.hspm.vikas_recipe_app.data.model.RecipeResponse

interface RecipeApi {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String,
        @Query("apiKey") apiKey: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true,
        @Query("fillIngredients") fillIngredients: Boolean = true,
        @Query("addRecipeNutrition") addRecipeNutrition: Boolean = true,
        @Query("number") number: Int = 10,
        @Query("language") language: String = "ru"
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("language") language: String = "ru"
    ): Recipe
} 