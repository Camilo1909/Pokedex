package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.ActivityPokemonCatchBinding

class PokemonCatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonCatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonCatchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.catchBt.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}