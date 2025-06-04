package ru.hspm.vikas_recipe_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.hspm.vikas_recipe_app.R
import ru.hspm.vikas_recipe_app.data.model.Recipe

class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit,
    private val onFavoriteClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = emptyList()

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_card, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        private val recipeName: TextView = itemView.findViewById(R.id.recipeName)
        private val favoriteButton: ImageView = itemView.findViewById(R.id.favoriteButton)

        fun bind(recipe: Recipe) {
            recipeName.text = recipe.title

            Glide.with(itemView.context)
                .load(recipe.image)
//                .placeholder(R.drawable.placeholder_image)
//                .error(R.drawable.error_image)
                .centerCrop()
                .into(recipeImage)

            favoriteButton?.setImageResource(
                if (recipe.isFavorite) R.drawable.ic_like
                else R.drawable.ic_dislike
            )

            itemView.setOnClickListener { onRecipeClick(recipe) }
            favoriteButton?.setOnClickListener { onFavoriteClick(recipe) }
        }
    }
} 