package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.databinding.ActivityHomeBinding
import com.example.pokedex.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.seeBtn.setOnClickListener {
            val intent = Intent(this, PokemonViewActivity::class.java)
            startActivity(intent)
        }

        binding.catchBtn.setOnClickListener {
            val intent = Intent(this, PokemonCatchActivity::class.java)
            startActivity(intent)
        }
    }
}