package ru.hspm.vikas_recipe_app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DayFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        val dayName = findViewById<TextView>(R.id.dayName)
        val dayImage = findViewById<ImageView>(R.id.dayImage)
        val dayDescription = findViewById<TextView>(R.id.dayDescription)
        val backButton = findViewById<Button>(R.id.backButton)

        // Set static content (these are already set in XML, but you can set them here if needed)
        dayName.setText(R.string.text_day_main_name)
        dayImage.setImageResource(R.drawable.cezar)
        dayDescription.setText(R.string.text_day_recipe)

        backButton.setOnClickListener { finish() }
    }
}