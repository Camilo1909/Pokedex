package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.ActivityPokemonViewBinding

class PokemonViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.dropBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}