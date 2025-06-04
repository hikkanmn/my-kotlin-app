package ru.hspm.vikas_recipe_app.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("results")
    val results: List<Recipe>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("totalResults")
    val totalResults: Int
) : Serializable

data class Recipe(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("servings")
    val servings: Int,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient>,
    @SerializedName("nutrition")
    val nutrition: Nutrition?,
    var isFavorite: Boolean = false
) : Serializable

data class Ingredient(
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unit")
    val unit: String
) : Serializable

data class Nutrition(
    @SerializedName("calories")
    val calories: Double,
    @SerializedName("protein")
    val protein: Double,
    @SerializedName("fat")
    val fat: Double,
    @SerializedName("carbohydrates")
    val carbohydrates: Double
) : Serializable 